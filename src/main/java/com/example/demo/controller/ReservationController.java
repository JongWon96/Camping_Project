//package com.example.demo.controller;
//
//import java.sql.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.demo.domain.Camping;
//import com.example.demo.domain.Member;
//import com.example.demo.domain.Reservation;
//import com.example.demo.service.CampingService;
//import com.example.demo.service.ReservationService;
//
//import jakarta.servlet.http.HttpSession;
//@Controller
//public class ReservationController {
//
//    @Autowired
//    private ReservationService reservationService;
//
//    @Autowired
//    private CampingService campingService;
//    
//    // 예약 페이지로 이동 (방 번호도 함께 전달)
//    @GetMapping("/reservePage/{campingId}/{roomType}")
//    public String showReservationPage(@PathVariable Long campingId, @PathVariable int roomType, Model model) {
//        Camping camping = campingService.findById(campingId);
//        model.addAttribute("camping", camping);
//        model.addAttribute("roomType", roomType); // 방 종류를 전달
//        return "reservationPage"; // 예약 페이지
//    }
//
//    @PostMapping("/reserve")
//    public String reserve(@RequestParam Long campingId, 
//                          @RequestParam int roomType, 
//                          @RequestParam String checkin, 
//                          @RequestParam String checkout, 
//                          @RequestParam int person, 
//                          HttpSession session) {
//
//        // 로그인한 유저 가져오기
//        Member member = (Member) session.getAttribute("member");
//        if (member == null) {
//            return "redirect:/login"; // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
//        }
//
//        // 캠핑장 정보 가져오기
//        Camping camping = campingService.findById(campingId);
//
//        // 예약 처리
//        Reservation reservation = new Reservation();
//        reservation.setCamping(camping);
//        reservation.setMember(member);
//        reservation.setCheckin(Date.valueOf(checkin));
//        reservation.setCheckout(Date.valueOf(checkout));
//        reservation.setPerson(person);
//
//        // 방 유형 저장 (작은 방 = 1, 중간 방 = 2, 큰 방 = 3)
//        reservation.setRoomType(roomType);
//
//        // 예약 저장
//        reservationService.save(reservation);
//
//        // 예약 완료 후 예약 내역 페이지로 이동
//        return "redirect:/reservations";
//    }
//}
//
//	@PostMapping("/reserve")
//	public String reserve(@RequestParam Long campingId, @RequestParam String checkin, @RequestParam String checkout,
//	                      @RequestParam int person, @RequestParam String siteType, HttpSession session) {
//	    Member member = (Member) session.getAttribute("member");
//	    if (member == null) {
//	        return "redirect:/login";  // 로그인하지 않으면 로그인 페이지로 리다이렉트
//	    }
//	    
//	    Camping camping = campingService.findById(campingId);
//	    Reservation reservation = new Reservation();
//	    reservation.setCamping(camping);
//	    reservation.setMember(member);
//	    reservation.setCheckin(Date.valueOf(checkin));
//	    reservation.setCheckout(Date.valueOf(checkout));
//	    reservation.setPerson(person);
//	    reservation.setSiteType(siteType);
//
//	    reservationService.save(reservation);  // 예약 정보 저장
//	    return "redirect:/reservations";  // 예약 완료 후 예약 내역 페이지로 이동
//	}
//
//}
