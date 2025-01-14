package com.example.demo.controller;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.Member;
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
                               HttpSession session) throws IOException {

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        Long memberId = loginUser.getId();

        // 리뷰 저장 (이미지 파일을 전달)
        reviewService.saveReview(memberId, campingId, content, rate, imgFile);

        return "redirect:/success"; // 리뷰 작성 후 성공 페이지로 이동
    }

    // 후기 작성 성공 페이지
    @GetMapping("/success")
    public String reviewSuccess() {
        return "review/review_success"; // 후기 작성 성공 페이지
    }

//	@GetMapping("/rewiews")
//	public String rewiewList(@RequestParam,){getReviewsByCampingId
}

