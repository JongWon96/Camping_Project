package com.example.demo.persistence;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Likes;
import com.example.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    // 특정 회원의 찜 목록 조회
    List<Likes> findByMember(Member member);

    // 특정 회원과 캠핑장을 기준으로 찜 데이터 조회
    Optional<Likes> findByMemberAndCamping(Member member, Camping camping);

    boolean existsByMemberAndCamping(Member member, Camping randomCamping);

    // 찜 삭제는 JpaRepository의 기본 delete() 메서드를 사용
}
