package com.example.demo.controller;

import java.sql.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Member;
import com.example.demo.domain.Reservation;
import com.example.demo.domain.Product;
import com.example.demo.service.CampingService;
import com.example.demo.service.ReservationService;
import com.example.demo.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CampingService campingService;

    // 예약 페이지 보여주기
    @GetMapping("/reservationPage/{campingId}/{room}")
    public String showReservationPage(@PathVariable Long campingId, @PathVariable int room, 
                                      @RequestParam(required = false) String checkin, 
                                      @RequestParam(required = false) String checkout, 
                                      Model model) {

        // 캠핑장 및 방 정보 가져오기
        Camping camping = campingService.findById(campingId);
        Product product = productService.findByCamping_IdAndRoom(campingId, room);

        // 기본 날짜 설정 (오늘부터 내일로 설정)
        Date checkinDate = new Date(System.currentTimeMillis()); // 오늘
        Date checkoutDate = new Date(System.currentTimeMillis() + 86400000L); // 하루 후 (1일: 86400000ms)

        if (checkin != null && checkout != null) {
            try {
                // 사용자가 날짜를 입력한 경우, 날짜 형식 변환
                checkinDate = Date.valueOf(checkin);
                checkoutDate = Date.valueOf(checkout);
            } catch (IllegalArgumentException e) {
                model.addAttribute("message", "잘못된 날짜 형식입니다. 올바른 날짜를 입력해주세요.");
                return "errorPage"; // 오류 페이지로 리다이렉트
            }
        }

        // 해당 날짜에 예약된 방 수 계산
        int remainingRoomCount = reservationService.getRemainingRooms(campingId, room, checkinDate, checkoutDate);

        model.addAttribute("camping", camping);
        model.addAttribute("room", room);
        model.addAttribute("maxRoomCount", product.getRoomcount());  // 전체 방 개수
        model.addAttribute("remainingRoomCount", remainingRoomCount);  // 남은 방 개수
        model.addAttribute("checkin", checkinDate.toString());
        model.addAttribute("checkout", checkoutDate.toString());

        return "/Reservation/reservationPage"; // 예약 페이지
    }

    // 예약 처리
    @PostMapping("/reservation")
    public String reserve(@RequestParam Long campingId, 
                          @RequestParam int room, 
                          @RequestParam String checkin, 
                          @RequestParam String checkout, 
                          @RequestParam int person, 
                          @RequestParam(value = "bottom", required = false) String bottom,
                          HttpSession session,
                          Model model) {

        // 로그인된 사용자 확인
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            session.invalidate(); // 세션 무효화
            return "redirect:/login";  // 로그인되지 않으면 로그인 페이지로 리다이렉트
        }

        // 캠핑장 정보 가져오기
        Camping camping = campingService.findById(campingId);

        // 방 정보 가져오기 (Product에서 캠핑장 ID와 방 타입으로 찾기)
        Product product = productService.findByCamping_IdAndRoom(campingId, room);

        // 날짜 형식 검증
        Date checkinDate;
        Date checkoutDate;
        try {
            checkinDate = Date.valueOf(checkin);  // 체크인 날짜 설정
            checkoutDate = Date.valueOf(checkout);  // 체크아웃 날짜 설정
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", "잘못된 날짜 형식입니다. 올바른 날짜를 입력해주세요.");
            return "/Reservation/reservationPage";
        }

        // 방 갯수 확인 (날짜별로 남은 방 수 계산)
        int remainingRoomCount = getRemainingRooms(campingId, room, checkinDate, checkoutDate);

        if (remainingRoomCount <= 0) {
            model.addAttribute("message", "현재 예약 가능한 방이 없습니다.");
            return "noAvailableRooms"; // noAvailableRooms 페이지에서 메시지 출력
        }

        // 예약 처리
        Reservation reservation = new Reservation();
        reservation.setProduct(product);  
        reservation.setMember(member);    
        reservation.setCheckin(checkinDate);  
        reservation.setCheckout(checkoutDate);  
        reservation.setPerson(person);  
        reservation.setBottom(bottom); 

        // 방 갯수 차감 (트랜잭션을 고려하여 처리)
        productService.reduceRoomCount(campingId, room);

        // 예약 저장
        reservationService.save(reservation);

        // 예약 완료 후 예약 내역 페이지로 리다이렉트
        return "redirect:/Reservation/reservation_details";
    }

    // 특정 날짜에 남은 방 개수 계산
    public int getRemainingRooms(Long campingId, int room, Date checkinDate, Date checkoutDate) {
        // 해당 캠핑장, 방 타입, 날짜 범위에 대한 예약 목록을 조회
        List<Reservation> reservations = reservationService.findReservationsByCampingIdAndRoomAndDateRange(campingId, room, checkinDate, checkoutDate);
        
        // 예약된 방 수
        int reservedRooms = reservations.size();
        
        // 해당 방의 총 방 개수에서 예약된 방을 차감
        Product product = productService.findByCamping_IdAndRoom(campingId, room);
        int remainingRooms = product.getRoomcount() - reservedRooms;

        return remainingRooms;
    }

    // 남은 방 개수를 계산하는 엔드포인트 추가
    @GetMapping("/checkRemainingRooms")
    public ResponseEntity<Map<String, Integer>> checkRemainingRooms(@RequestParam Long campingId, 
                                                                   @RequestParam int room, 
                                                                   @RequestParam String checkin, 
                                                                   @RequestParam String checkout) {
        Date checkinDate = Date.valueOf(checkin);
        Date checkoutDate = Date.valueOf(checkout);

        int remainingRoomCount = getRemainingRooms(campingId, room, checkinDate, checkoutDate);

        Map<String, Integer> response = new HashMap<>();
        response.put("remainingRooms", remainingRoomCount);

        return ResponseEntity.ok(response);
    }
}
