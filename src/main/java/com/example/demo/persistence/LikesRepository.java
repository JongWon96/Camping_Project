package com.example.demo.persistence;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Likes;
import com.example.demo.domain.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    boolean existsByMemberIdAndCampingId(Long memberId, Long campingId); // ID 기반 존재 확인

    @Transactional
    void deleteByMemberIdAndCampingId(Long memberId, Long campingId); // ID 기반 삭제

    @Query("SELECT r FROM Likes r WHERE r.member = :member ORDER BY r.id DESC")
    List<Likes> findByMember(Member member);

    boolean existsByMemberAndCamping(Member member, Camping camping);

    Optional<Object> findByMemberAndCamping(Member member, Camping camping);
}
