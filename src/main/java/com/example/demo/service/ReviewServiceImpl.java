//package com.example.demo.service;
//
//import com.example.demo.domain.Review;
//import com.example.demo.domain.Member;
//import com.example.demo.domain.Camping;
//import com.example.demo.persistence.ReviewRepository;
//import com.example.demo.persistence.MemberRepository;
//import com.example.demo.persistence.CampingRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class ReviewServiceImpl implements ReviewService {
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private CampingRepository campingRepository;
//
//    // 후기 저장
//    public Review saveReview(Long memberId, Long campingId, String content, Integer rate, String img, Integer danger) {
//        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
//        Camping camping = campingRepository.findById(campingId).orElseThrow(() -> new RuntimeException("Camping not found"));
//
//        Review review = Review.builder()
//                .member(member)
//                .camping(camping)
//                .content(content)
//                .rate(rate)
//                .img(img)
//                .danger(danger)
//                .date(new Date())  // 현재 날짜로 설정
//                .build();
//
//        return reviewRepository.save(review);
//    }
//}
