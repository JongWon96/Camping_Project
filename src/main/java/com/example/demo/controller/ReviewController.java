package com.example.demo.controller;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.Review;
import com.example.demo.domain.Camping;
import com.example.demo.domain.Member;
import com.example.demo.service.CampingService;
import com.example.demo.service.ReservationService;
import com.example.demo.service.ReviewService;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ReviewController {

    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private CampingService campingService;

    @Autowired
    private ReviewService reviewService;

    // 예약 상세 정보를 보여주는 메소드
    @GetMapping("/reservationDetails")
    public String viewReservationDetails(HttpSession session, Model model) {
        // 세션에서 로그인된 사용자 정보 가져오기
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        // 로그인된 사용자의 ID
        Long memberId = loginUser.getId();

        // 로그인된 사용자의 예약 정보를 가져오기
        List<Reservation> reservations = reservationService.findReservationsByMember(loginUser);
        if (reservations.isEmpty()) {
            model.addAttribute("errorMessage", "예약 정보를 찾을 수 없습니다.");
            return "errorPage";  // 예약이 없을 경우 errorPage를 반환
        }
  
        // 각 예약에 대해 후기가 작성되었는지 확인하고, 예약 객체에 추가
        for (Reservation reservation : reservations) {
            boolean hasReview = reviewService.hasReview(reservation.getMember().getId(), reservation.getProduct().getCamping().getId());
            reservation.setHasreview(hasReview);
        }
        
        // 모델에 예약 정보 리스트를 추가
        model.addAttribute("reservations", reservations);

        // 예약 상세 페이지로 이동
        return "mypage/reservations"; // Thymeleaf 템플릿에서 예약 리스트를 반복 처리
    }
    

    @PostMapping("/cancelReservation")
    public String cancelReservation(@RequestParam Long reservationId, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 로그인 상태 확인
        }

        // 예약 취소 메소드 호출
        reservationService.cancelReservation(reservationId, loginUser);

        // 예약 취소 후 예약 목록 페이지로 리다이렉트
        return "redirect:/reservationDetails";
    }

    // 후기 작성 가능 여부 확인
    @GetMapping("/checkReview")
    public String checkReviewPage(@RequestParam Long campingId, HttpSession session, Model model) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        Long memberId = loginUser.getId();
        boolean canWriteReview = reservationService.isReviewAvailable(campingId, memberId);

        if (canWriteReview) {
            model.addAttribute("campingId", campingId);
            model.addAttribute("memberId", memberId);
            return "Review/write_review"; // 후기 작성 페이지
        } else {
            return "reservation/after_reservation"; // 후기 작성 불가 페이지
        }
    }

    // 후기 작성 폼
    @GetMapping("/write")
    public String showWriteReviewForm(@RequestParam Long campingId, HttpSession session, Model model) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        Long memberId = loginUser.getId();
        model.addAttribute("campingId", campingId);
        model.addAttribute("memberId", memberId);
        return "Review/write_review";
    }

    // 후기 제출 처리
    @PostMapping("/submit")
    public String submitReview(@RequestParam Long campingId, 
                               @RequestParam String content, 
                               @RequestParam Integer rate, 
                               @RequestParam(required = false) MultipartFile imgFile, 
                               @RequestParam(required = false) Integer danger, 
                               HttpSession session) throws IOException {

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }
        // danger 값이 없거나 null이면 기본값 0으로 설정
        if (danger == null) {
            danger = 0;  // 기본값 0으로 설정
        }
        Long memberId = loginUser.getId();

        // 리뷰 저장 (이미지 파일을 전달)
        reviewService.saveReview(memberId, campingId, content, rate, imgFile ,danger);

        return "redirect:/success"; // 리뷰 작성 후 성공 페이지로 이동
    }

    // 후기 작성 성공 페이지
    @GetMapping("/success")
    public String reviewSuccess() {
        return "review/review_success"; // 후기 작성 성공 페이지
    }
    @GetMapping("/camping/{campingId}/reviews")
    public String viewReviews(@PathVariable Long campingId, HttpSession session, Model model) {
        // 캠핑장 정보 가져오기
        Camping camping = campingService.getCampingDetail(campingId); // 캠핑장 정보를 가져오는 서비스 호출
        model.addAttribute("camping", camping);

        // 로그인된 사용자 정보 가져오기
        Member loginUser = (Member) session.getAttribute("loginUser");

        // 해당 캠핑장에 대한 리뷰 목록 가져오기
        List<Review> reviews = reviewService.getReviewsByCampingId(campingId);
        model.addAttribute("reviews", reviews);
        model.addAttribute("loginUser", loginUser);  // 로그인한 사용자 정보를 모델에 추가

        return "Camping/reviews"; // 캠핑장 리뷰 페이지 뷰 반환
    }

    
    @GetMapping("/camping/{campingId}/reviews/{reviewId}/edit")
    public String showEditReviewForm(@PathVariable Long campingId, 
                                     @PathVariable Long reviewId, 
                                     HttpSession session, 
                                     Model model) {
        // 리뷰 정보 가져오기
        Review review = reviewService.getReviewById(reviewId);

        // 로그인된 사용자 정보
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null || !loginUser.getId().equals(review.getMember().getId())) {
            // 로그인되지 않았거나, 로그인된 사용자가 리뷰 작성자가 아닌 경우
            return "redirect:/login"; // 로그인 페이지로 리다이렉트 (또는 에러 페이지로 리다이렉트 가능)
        }

        // 캠핑장 정보 가져오기
        Camping camping = campingService.getCampingDetail(campingId);

        // 모델에 리뷰 정보와 캠핑장 정보를 추가
        model.addAttribute("review", review);
        model.addAttribute("camping", camping);

        // 수정 폼을 보여주는 뷰 이름 반환
        return "Review/edit_review"; // 수정 폼 템플릿을 반환
    }

    @PostMapping("/camping/{campingId}/reviews/{reviewId}")
    public String updateReview(@PathVariable Long campingId, 
                               @PathVariable Long reviewId, 
                               @RequestParam String content, 
                               @RequestParam Integer rate,
                               @RequestParam(required = false) MultipartFile imgFile,
                               @RequestParam(required = false) Boolean deleteImage, // 기존 이미지 삭제 여부
                               @RequestParam(required = false) Integer danger, // danger 값 추가
                               HttpSession session) throws IOException {

        // 리뷰 정보 가져오기
        Review review = reviewService.getReviewById(reviewId);

        // 로그인된 사용자 정보
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null || !loginUser.getId().equals(review.getMember().getId())) {
            // 로그인되지 않았거나, 로그인된 사용자가 리뷰 작성자가 아닌 경우
            return "redirect:/login"; // 로그인 페이지로 리다이렉트 (또는 에러 페이지로 리다이렉트 가능)
        }

        // danger 값이 없거나 null이면 기본값 0으로 설정
        if (danger == null) {
            danger = 0;  // 기본값 0으로 설정
        }

        // 기존 이미지를 삭제해야 하는지 확인
        if (deleteImage != null && deleteImage) {
            // 사용자가 이미지 삭제를 요청한 경우
            reviewService.deleteReviewImage(review); // 서비스에서 기존 이미지 삭제 처리
            review.setImg(null); // 이미지 경로 초기화
        }

        // 리뷰 수정 로직
        reviewService.updateReview(reviewId, content, rate, imgFile, danger); // danger 값을 함께 전달

        return "redirect:/camping/" + campingId + "/reviews"; // 수정 후 리다이렉트
    }

    @PostMapping("/camping/{campingId}/reviews/{reviewId}/delete")
    public String deleteReview(@PathVariable Long campingId, 
                               @PathVariable Long reviewId, 
                               HttpSession session) throws IOException {
        // 리뷰 정보 가져오기
        Review review = reviewService.getReviewById(reviewId);

        // 로그인된 사용자 정보
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null || !loginUser.getId().equals(review.getMember().getId())) {
            // 로그인되지 않았거나, 로그인된 사용자가 리뷰 작성자가 아닌 경우
            return "redirect:/login"; // 로그인 페이지로 리다이렉트 (또는 에러 페이지로 리다이렉트 가능)
        }

        // 리뷰 삭제 로직
        reviewService.deleteReview(reviewId);  // 리뷰 삭제

        // 이미지를 삭제하는 로직 (이미지 삭제 메서드에서 처리)
        // 해당 리뷰가 이미지 파일을 가지고 있다면 이미지도 삭제
        return "redirect:/camping/" + campingId + "/reviews";  // 삭제 후 리다이렉트
    }
    
    
    @PostMapping("/camping/{campingId}/reviews/{reviewId}/report")
    public String reportReview(@PathVariable Long campingId, @PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        if (review != null) {
            // 리뷰의 danger 값을 1로 설정하여 신고됨 상태로 변경
            review.setDanger(1);
            reviewService.dangerReview(review); // 변경된 리뷰 저장
        }
        return "redirect:/camping/" + campingId + "/reviews"; // 리뷰 목록 페이지로 리다이렉트
    
    }
    
    
}

