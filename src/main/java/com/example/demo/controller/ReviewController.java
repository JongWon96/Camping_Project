//package com.example.demo.controller;
//
//import com.example.demo.domain.Product;
//import com.example.demo.domain.Reservation;
//import com.example.demo.service.ProductService;
//import com.example.demo.service.ReservationService;
//import com.example.demo.service.ReviewService;
//
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/reviews")
//public class ReviewController {
//
//	@Autowired
//    private ReservationService reservationService;
//
//
//	@Autowired
//    private ProductService productService;
//	
//    @Autowired
//    private ReviewService reviewService;
//
//
// // 예약 정보 페이지로 이동
//    @GetMapping("/reservationDetails")
//    public String viewReservationDetails(@RequestParam Long campingId, @RequestParam Long memberId, Model model) {
//     
//        Reservation reservation = reservationService.getReservationByIds(campingId, memberId);
//        
//        // 예약 정보를 모델에 추가
//        model.addAttribute("campingId", campingId);
//        model.addAttribute("memberId", memberId);
//        model.addAttribute("campingName", reservation.getProduct().getCamping().getFacltnm());
//        model.addAttribute("CheakIn", reservation.getCheckin());
//        model.addAttribute("CheakOut", reservation.getCheckout());
//        model.addAttribute("memberName", reservation.getMember().getName());
//
//        // 후기 작성 가능 여부 확인
//        boolean canWriteReview = reservationService.isReviewAvailable(campingId, memberId);
//        model.addAttribute("canWriteReview", canWriteReview);
//
//        return "reservation/reservation_details";  // 예약 정보 페이지로 이동
//    }
//    // 예약 취소 처리
//  @PostMapping("/cancelReservation/{reservationId}")
//  public String cancelReservation(@PathVariable Long reservationId, Model model) {
//      // 예약 정보 조회
//      Reservation reservation = reservationService.findById(reservationId);
//
//      if (reservation != null) {
//          // 예약 체크인 날짜
//          LocalDate checkinDate = reservation.getCheckin().toLocalDate();
//
//          // 현재 날짜
//          LocalDate currentDate = LocalDate.now();
//
//          // 예약일로부터 10일 전까지 취소 가능
//          long daysBetween = ChronoUnit.DAYS.between(currentDate, checkinDate);
//
//          if (daysBetween > 10) {
//              // 예약 취소 처리
//              Product product = reservation.getProduct();  // Product 객체를 가져옵니다.
//              int room = product.getRoom(); // Product에서 방 타입 가져오기
//              productService.restoreRoomCount(product.getCamping().getId(), room);  // 방 갯수 복구
//
//              // 예약 취소
//              reservationService.cancelReservation(reservationId);
//
//              model.addAttribute("message", "예약이 취소되었습니다.");
//          } else {
//              // 10일 이내 취소 불가
//              model.addAttribute("message", "예약일로부터 10일 전까지만 예약 취소가 가능합니다.");
//          }
//      } else {
//          model.addAttribute("message", "예약을 찾을 수 없습니다.");
//      }
//
//      // 예약 내역 페이지로 리다이렉트
//      return "redirect:/reservation_details";
//  }
//
//    // 후기 작성 가능 여부 확인
//    @GetMapping("/checkReview")
//    public String checkReviewPage(@RequestParam Long campingId, @RequestParam Long memberId, Model model) {
//        boolean canWriteReview = reservationService.isReviewAvailable(campingId, memberId);
//        
//        if (canWriteReview) {
//            model.addAttribute("campingId", campingId);
//            model.addAttribute("memberId", memberId);
//            return "Review/write_review";  // 후기 작성 페이지로 이동
//        } else {
//            return "reservation/after_reservation";  // 후기 작성 불가 페이지로 이동
//        }
//    }
//
//    
//    // 후기 작성 폼 페이지
//    @GetMapping("/write")
//    public String showWriteReviewForm(@RequestParam Long campingId, @RequestParam Long memberId, Model model) {
//        model.addAttribute("campingId", campingId);
//        model.addAttribute("memberId", memberId);
//        return "Review/write_review";  // 
//    }
//
//    // 후기 제출 처리
//    @PostMapping("/submit")
//    public String submitReview(@RequestParam Long memberId, @RequestParam Long campingId,
//                               @RequestParam String content, @RequestParam Integer rate,
//                               @RequestParam(required = false) String img, @RequestParam Integer danger) {
//        reviewService.saveReview(memberId, campingId, content, rate, img, danger);
//        return "redirect:/reviews/success";  // 후기 작성 후 성공 페이지로 이동
//    }
//
//    // 후기 작성 성공 페이지
//    @GetMapping("/success")
//    public String reviewSuccess() {
//        return "review_success";  // 후기 성공 페이지
//    }
//}
