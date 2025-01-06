//package com.example.demo.controller;
//
//import com.example.demo.service.ReviewService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/reviews")
//public class ReviewController {
//
//    @Autowired
//    private ReviewService reviewService;
//
//    // 후기 작성 폼 페이지
//    @GetMapping("/write")
//    public String showWriteReviewForm(@RequestParam Long campingId, @RequestParam Long memberId, Model model) {
//        model.addAttribute("campingId", campingId);
//        model.addAttribute("memberId", memberId);
//        return "write_review";  // write_review.html 페이지로 이동
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
