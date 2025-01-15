package com.example.demo.service;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Likes;
import com.example.demo.domain.Member;
import com.example.demo.persistence.CampingRepository;
import com.example.demo.persistence.LikesRepository;
import com.example.demo.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesServiceImpl implements LikesService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    CampingRepository campingRepository;

    @Override
    public List<Likes> getLikesByMember(String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        return likesRepository.findByMember(member);
    }

    @Override
    public void removeLike(String memberId, Long campingId) {
        // 회원 조회
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        // 캠핑장 조회
        Camping camping = campingRepository.findById(campingId)
                .orElseThrow(() -> new RuntimeException("캠핑 정보를 찾을 수 없습니다."));

        // 찜 데이터 조회
        Likes like = (Likes) likesRepository.findByMemberAndCamping(member, camping)
                .orElseThrow(() -> new RuntimeException("찜 데이터를 찾을 수 없습니다."));

        // 삭제
        likesRepository.delete(like);
    }
}
