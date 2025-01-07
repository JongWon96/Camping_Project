package com.example.demo.controller;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // 예약 페이지
    @GetMapping("/reservationPage/{campingId}/{room}")
    public String showReservationPage(@PathVariable Long campingId, @PathVariable int room, Model model) {
    	
    		
        Camping camping = campingService.findById(campingId);
        
        // 방 정보 가져오기 (Product에서 캠핑장 ID와 방 타입으로 찾기)
        Product product = productService.findByCamping_IdAndRoom(campingId, room);

        // 전체 방 개수와 남은 방 개수 전달
      //  int maxRoomCount = product.getMaxRoomCount();  // 전체 방 개수
        int remainingRoomCount = product.getRoomcount();  // 남은 방 개수
        
        model.addAttribute("camping", camping);
        model.addAttribute("room", room);  // 방 타입
      //  model.addAttribute("maxRoomCount", maxRoomCount);  // 전체 방 개수
        model.addAttribute("remainingRoomCount", remainingRoomCount);  // 남은 방 개수

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

        // 방 갯수 확인
        if (product.getRoomcount() <= 0) {
            model.addAttribute("message", "현재 예약 가능한 방이 없습니다.");
            return "noAvailableRooms"; // noAvailableRooms 페이지에서 메시지 출력
        }

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

        // 예약 처리
        Reservation reservation = new Reservation();
        reservation.setProduct(product);  // Product를 외래키로 설정 (Product가 이미 캠핑 정보 포함)
        reservation.setMember(member);    // 사용자 설정
        reservation.setCheckin(checkinDate);  
        reservation.setCheckout(checkoutDate);  
        reservation.setPerson(person);  // 인원 수 설정
        reservation.setBottom(bottom);  // 바닥재 선택 값 저장

        // 방 갯수 차감 (트랜잭션을 고려하여 처리)
        productService.reduceRoomCount(campingId, room);

        // 예약 저장
        reservationService.save(reservation);

        // 예약 완료 후 예약 내역 페이지로 리다이렉트
        return "redirect:/reservations";
    }

    // 예약 취소 처리
    @PostMapping("/cancelReservation/{reservationId}")
    public String cancelReservation(@PathVariable Long reservationId, Model model) {
        // 예약 정보 조회
        Reservation reservation = reservationService.findById(reservationId);

        if (reservation != null) {
            // 방 갯수 복구 (ProductService의 restoreRoomCount 사용)
            Product product = reservation.getProduct();  // Product 객체를 가져옵니다.
            int room = product.getRoom(); // Product에서 방 타입 가져오기
            productService.restoreRoomCount(product.getCamping().getId(), room);  // 방 갯수 복구

            // 예약 취소
            reservationService.cancelReservation(reservationId);

            // 예약 취소 후 피드백 메시지 전달
            model.addAttribute("message", "예약이 취소되었습니다.");
        } else {
            model.addAttribute("message", "예약을 찾을 수 없습니다.");
        }

        // 예약 내역 페이지로 리다이렉트
        return "redirect:/reservations";
    }
}
