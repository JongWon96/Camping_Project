package com.example.demo.controller;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Member;
import com.example.demo.domain.Product;
import com.example.demo.domain.Reservation;
import com.example.demo.service.CampingService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReservationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReservationContoller {

    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CampingService campingService;

    @GetMapping("/reservationpage")
    private String reservationPage(@RequestParam("campingid") Long campingId, 
                                   @RequestParam("room") Integer roomNum, 
                                   @RequestParam(required = false) String checkin, 
                                   @RequestParam(required = false) String checkout, 
                                   Model model) {
    	  System.out.println("Received room number: " + roomNum);  // 디버깅을 위한 로그
        // 날짜 형식 변환
    	 Date checkinDate = new Date(System.currentTimeMillis()); // 오늘
         Date checkoutDate = new Date(System.currentTimeMillis() + 86400000L); // 하루 후 (1일: 86400000ms
         

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

      
         
        // 예약 정보와 남은 방 개수 가져오기
        Product product = productService.getProductByRoom(campingId, roomNum);
        int remainingRoomCount = reservationService.getRemainingRooms(campingId, roomNum, checkinDate, checkoutDate);

        // 다른 방 두 개
        List<Product> otherRooms = productService.getProducts(campingId);

        // 선택된 방을 제외한 다른 방 리스트로 필터링
        otherRooms = otherRooms.stream()
                               .filter(room -> room.getRoom() != roomNum)
                               .collect(Collectors.toList());

        // 다른 방 정보와 가격 포맷 처리
        Product otherRoom1 = otherRooms.get(0);
        int price1 = (int) otherRoom1.getPrice().doubleValue();
        String formattedPrice1 = NumberFormat.getNumberInstance(Locale.KOREA).format(price1);

        Product otherRoom2 = otherRooms.get(1);
        int price2 = (int) otherRoom2.getPrice().doubleValue();
        String formattedPrice2 = NumberFormat.getNumberInstance(Locale.KOREA).format(price2);

        // 모델에 데이터 추가
        model.addAttribute("product", product);
        model.addAttribute("remainingRoomCount", remainingRoomCount);  // 남은 방 개수
        model.addAttribute("room", product.getRoom());
        
        model.addAttribute("otherRoom1", otherRoom1);
        model.addAttribute("price1", formattedPrice1);
        model.addAttribute("otherRoom2", otherRoom2);
        model.addAttribute("price2", formattedPrice2);

        int price = (int) product.getPrice().doubleValue();
        String formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(price);
        model.addAttribute("price", formattedPrice);
        model.addAttribute("campingid", campingId);

        // 남은 방 개수가 0일 때 처리
        if (remainingRoomCount == 0) {
            model.addAttribute("noRoomsMessage", "선택한 날짜에 예약 가능한 방이 없습니다.");
        }

        return "Camping/reservationPage";
    }

    
 // 남은 방 개수를 계산하는 엔드포인트 추가
    @GetMapping("/checkRemainingRooms")
    public ResponseEntity<Map<String, Integer>> checkRemainingRooms(@RequestParam Long campingId, 
                                                                   @RequestParam int room, 
                                                                   @RequestParam String checkin, 
                                                                   @RequestParam String checkout) {
        
    
			System.out.println("Check-in: " + checkin);
			System.out.println("Check-out: " + checkout);
			System.out.println("Camping ID: " + campingId);
			System.out.println("Room: " + room);


    	Date checkinDate = Date.valueOf(checkin);
        Date checkoutDate = Date.valueOf(checkout);

        // 남은 방 개수 계산
        int remainingRoomCount = reservationService.getRemainingRooms(campingId, room, checkinDate, checkoutDate);

        // 결과를 Map에 담아 반환
        Map<String, Integer> response = new HashMap<>();
        response.put("remainingRooms", remainingRoomCount);

        return ResponseEntity.ok(response);
    }

    // 예약 처리
    @PostMapping("/reservation")
    public String reserve(@RequestParam("campingid") Long campingid, 
                          @RequestParam("room") int room, 
                          @RequestParam String checkin, 
                          @RequestParam String checkout, 
                          @RequestParam int person, 
                          @RequestParam(value = "bottom", required = false) String bottom,
                          HttpSession session,
                          Model model) {

        // 로그인된 사용자 확인
        Member member = (Member) session.getAttribute("loginUser");
        if (member == null) {
            session.invalidate(); // 세션 무효화
            return "redirect:/login";  // 로그인되지 않으면 로그인 페이지로 리다이렉트
        }

        // 캠핑장 정보 가져오기
        Camping camping = campingService.findById(campingid);

        // 방 정보 가져오기 (Product에서 캠핑장 ID와 방 타입으로 찾기)
        Product product = productService.findByCamping_IdAndRoom(campingid, room);

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
        int remainingRoomCount = reservationService.getRemainingRooms(campingid, room, checkinDate, checkoutDate);

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

        // 예약 저장
        reservationService.save(reservation);

        // 예약 완료 후 예약 내역 페이지로 리다이렉트
        return "redirect:/camping_details";
    }
//    @PostMapping("/reservation_success")
//    public String reservationSuccess(@RequestParam("id") Long id, Reservation reservation, HttpSession session) {
//
//        Member loginUser = (Member) session.getAttribute("loginUser");
//
//        String url = "";
//
//        if (loginUser == null) {
//            url = "member/login";  // 로그인되지 않으면 로그인 페이지로 리다이렉트
//        } else {
//            reservation.setMember(loginUser);
//
//            // 예약 처리
//            Product p = new Product();
//            p.setId(id);
//            reservation.setProduct(p);
//
//            // 예약 정보 저장
//            reservationService.save(reservation);
//
//            url = "mypage/reservation";  // 예약 완료 후 예약 내역 페이지로 리다이렉트
//        }
//
//        return url;
//    }
}
