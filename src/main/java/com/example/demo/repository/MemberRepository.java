package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 로그인 시 ID로 회원 정보를 찾을 수 있는 메소드 추가
    Member findByMemberId(String memberId);
}
