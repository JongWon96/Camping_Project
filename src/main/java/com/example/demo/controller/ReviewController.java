package com.example.demo.controller;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.Member;
import com.example.demo.service.ReservationService;
import com.example.demo.service.ReviewService;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReviewService reviewService;

    /**
     * 예약 세부 정보 보기
     */
    @GetMapping("/reservationDetails")
    public String viewReservationDetails(HttpSession session, Model model) {
        // 세션에서 로그인된 사용자 정보 가져오기
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        // 로그인된 사용자의 ID
        Long memberId = loginUser.getId();

        // Reservation 가져오기 (여러 개의 예약을 조회)
        List<Reservation> reservations = reservationService.findReservationsByMember(loginUser);
        if (reservations.isEmpty()) {
            model.addAttribute("errorMessage", "예약 정보를 찾을 수 없습니다.");
            return "errorPage";
        }

        // 예약이 존재한다면, 첫 번째 예약을 사용하거나 다른 방식으로 처리할 수 있습니다.
        Reservation reservation = reservations.get(0); // 예시로 첫 번째 예약을 가져옴

        // Camping ID 가져오기
        Long campingId = reservation.getProduct().getCamping().getId();

        // 모델 데이터 추가
        model.addAttribute("campingId", campingId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("campingName", reservation.getProduct().getCamping().getFacltnm());
        model.addAttribute("checkIn", reservation.getCheckin());
        model.addAttribute("checkOut", reservation.getCheckout());
        model.addAttribute("memberName", reservation.getMember().getName());

        // 후기 작성 가능 여부
        boolean canWriteReview = reservationService.isReviewAvailable(campingId, memberId);
        model.addAttribute("canWriteReview", canWriteReview);

        return "mypage/reservations"; // 템플릿 렌더링
    }

    /**
     * 후기 작성 가능 여부 확인
     */
    @GetMapping("/checkReview")
    public String checkReviewPage(
            @RequestParam Long campingId,
            HttpSession session,
            Model model
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        Long memberId = loginUser.getId();
        boolean canWriteReview = reservationService.isReviewAvailable(campingId, memberId);

        if (canWriteReview) {
            model.addAttribute("campingId", campingId);
            model.addAttribute("memberId", memberId);
            return "Review/write_review";
        } else {
            return "reservation/after_reservation";
        }
    }

    /**
     * 후기 작성 폼 표시
     */
    @GetMapping("/write")
    public String showWriteReviewForm(
            @RequestParam Long campingId,
            HttpSession session,
            Model model
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        Long memberId = loginUser.getId();
        model.addAttribute("campingId", campingId);
        model.addAttribute("memberId", memberId);
        return "Review/write_review";
    }

    /**
     * 후기 제출 처리
     */
    @PostMapping("/submit")
    public String submitReview(
            @RequestParam Long campingId,
            @RequestParam String content,
            @RequestParam Integer rate,
            @RequestParam(required = false) String img,
            HttpSession session
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        Long memberId = loginUser.getId();

        // 이미지가 없는 경우 처리
        if (img == null || img.isEmpty()) {
            img = ""; // 기본 이미지로 설정 가능
        }

        // 리뷰 저장
        reviewService.saveReview(memberId, campingId, content, rate, img);
        return "redirect:/reviews/success";
    }

    /**
     * 후기 작성 성공 페이지
     */
    @GetMapping("/success")
    public String reviewSuccess() {
        return "review_success";
    }
}
