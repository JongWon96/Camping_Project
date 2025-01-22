package com.example.demo.service;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Likes;
import com.example.demo.domain.Member;
import com.example.demo.persistence.CampingRepository;
import com.example.demo.persistence.LikesRepository;
import com.example.demo.persistence.MemberRepository;
import jakarta.transaction.Transactional;
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
        Member member = memberRepository.findByMemberId(memberId);

        return likesRepository.findByMember(member);
    }

    @Override
    public void saveLike(Long memberId, Long campingId) {
        // 1. 멤버 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버를 찾을 수 없습니다: " + memberId));

        // 2. 캠핑 정보 확인
        Camping camping = campingRepository.findById(campingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 캠핑을 찾을 수 없습니다: " + campingId));


        // 4. 찜 데이터 저장
        Likes like = new Likes();
        like.setMember(member);
        like.setCamping(camping);
        likesRepository.save(like);
    }

    @Override
    public void removeLike(String memberId, Long campingId) {
        // 회원 조회
        Member member = memberRepository.findByMemberId(memberId);

        // 캠핑장 조회
        Camping camping = campingRepository.findById(campingId)
                .orElseThrow(() -> new RuntimeException("캠핑 정보를 찾을 수 없습니다."));

        // 찜 데이터 조회
        Likes like = (Likes) likesRepository.findByMemberAndCamping(member, camping)
                .orElseThrow(() -> new RuntimeException("찜 데이터를 찾을 수 없습니다."));

        // 삭제
        likesRepository.delete(like);
    }

    @Override
    @Transactional
    public void removeLike(Long memberId, Long campingId) {
        System.out.println("Removing like for Member ID: " + memberId + ", Camping ID: " + campingId);

        // 삭제 대상 확인
        boolean exists = likesRepository.existsByMemberIdAndCampingId(memberId, campingId);
        if (!exists) {
            throw new IllegalArgumentException("삭제하려는 찜 데이터가 존재하지 않습니다.");
        }

        // 데이터 삭제
        likesRepository.deleteByMemberIdAndCampingId(memberId, campingId);
        System.out.println("Like successfully removed for Member ID: " + memberId + ", Camping ID: " + campingId);
    }


    @Override
    public boolean isLiked(Long memberId, Long campingId) {
        // 1. 멤버 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버를 찾을 수 없습니다: " + memberId));

        // 2. 캠핑 정보 조회
        Camping camping = campingRepository.findById(campingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 캠핑을 찾을 수 없습니다: " + campingId));

        // 3. 찜 여부 확인
        return likesRepository.existsByMemberAndCamping(member, camping);
    }


}
