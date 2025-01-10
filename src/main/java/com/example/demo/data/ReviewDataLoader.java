package com.example.demo.data;/*package com.example.demo.persistence;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Member;
import com.example.demo.domain.Review;
import com.example.demo.persistence.CampingRepository;
import com.example.demo.persistence.MemberRepository;
import com.example.demo.persistence.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class ReviewDataLoader implements CommandLineRunner {

    private final ReviewRepository reviewRepository;
    private final CampingRepository campingRepository;
    private final MemberRepository memberRepository;

    public ReviewDataLoader(ReviewRepository reviewRepository, CampingRepository campingRepository, MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.campingRepository = campingRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Camping> campings = campingRepository.findAll();
        List<Member> members = memberRepository.findAll();

        Random random = new Random();

        for (Camping camping : campings) {
            int numberOfReviews = random.nextInt(4) + 2; // 2~5개의 리뷰 생성

            for (int i = 0; i < numberOfReviews; i++) {
                Review review = Review.builder()
                        .content(null) // 콘텐츠는 비워둠
                        .reviewdate(new Date())
                        .img(null) // 이미지 없음
                        .danger(random.nextInt(6)) // 위험도 0~5 사이의 값
                        .rate(random.nextInt(6)) // 평점 0~5 사이의 값
                        .member(members.get(random.nextInt(members.size()))) // 랜덤 멤버
                        .camping(camping) // 현재 캠핑장
                        .build();

                reviewRepository.save(review);
            }
        }

        System.out.println("모든 캠핑장에 리뷰 데이터 생성 완료!");
    }
}
