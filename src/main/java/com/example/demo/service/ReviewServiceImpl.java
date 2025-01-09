package com.example.demo.service;

import com.example.demo.domain.Review;
import com.example.demo.domain.Member;
import com.example.demo.domain.Camping;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.CampingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CampingRepository campingRepository;

    // 후기 저장
    @Override
    public Review saveReview(Long memberId, Long campingId, String content, Integer rate, String img, Integer danger) {
        // 회원과 캠핑장 정보 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        Camping camping = campingRepository.findById(campingId).orElseThrow(() -> new RuntimeException("Camping not found"));

        // Review 객체 생성
        Review review = Review.builder()
                .member(member)
                .camping(camping)
                .content(content)
                .rate(rate)
                .img(img)
                .danger(danger)  // 부적절한 내용 여부 (danger)
                .reviewdate(new Date())  // 현재 날짜로 설정
                .build();

        // 후기 저장
        Review savedReview = reviewRepository.save(review);

        // danger 값이 1이라면, 부적절한 후기 신고 처리를 추가
        if (danger != null && danger > 0) {
            alertAdminOfInappropriateReview(savedReview);  // 부적절한 후기 관리자에게 알림
        }

        return savedReview;
    }

    // 부적절한 후기 관리자에게 알림 (예시)
    private void alertAdminOfInappropriateReview(Review review) {
        // 예시: 관리자에게 이메일 발송, 시스템 알림 등
        System.out.println("부적절한 후기 발견! 관리자가 확인해야 합니다. 리뷰 내용: " + review.getContent());
    }
}
