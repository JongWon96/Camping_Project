/*package com.example.demo.service;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Member;
import com.example.demo.domain.Likes;
import com.example.demo.persistence.CampingRepository;
import com.example.demo.persistence.MemberRepository;
import com.example.demo.persistence.LikesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class LikesDataLoader implements CommandLineRunner {

    private final LikesRepository likesRepository;
    private final CampingRepository campingRepository;
    private final MemberRepository memberRepository;

    public LikesDataLoader(LikesRepository likesRepository, CampingRepository campingRepository, MemberRepository memberRepository) {
        this.likesRepository = likesRepository;
        this.campingRepository = campingRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Camping> campings = campingRepository.findAll();
        List<Member> members = memberRepository.findAll();

        Random random = new Random();

        for (Member member : members) {
            int numberOfLikes = random.nextInt(2) + 9; // 9~10개의 찜 생성

            for (int i = 0; i < numberOfLikes; i++) {
                Camping randomCamping = campings.get(random.nextInt(campings.size()));

                // 중복 찜 방지
                if (likesRepository.existsByMemberAndCamping(member, randomCamping)) {
                    continue;
                }

                Likes like = Likes.builder()
                        .member(member)
                        .camping(randomCamping)
                        .build();

                likesRepository.save(like);
            }
        }

        System.out.println("모든 멤버에게 랜덤 찜 데이터 생성 완료!");
    }
}
