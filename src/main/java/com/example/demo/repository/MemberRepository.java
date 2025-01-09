package com.example.demo.repository;

import com.example.demo.domain.Inquiry;
import com.example.demo.domain.Member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 로그인 시 ID로 회원 정보를 찾을 수 있는 메소드 추가
    Member findByMemberId(String memberId);
    
    // Member ID로 회원 검색
    Member findMemberByMemberId(String memberId);

    // 이름과 전화번호로 회원 검색
    Member findByNameAndPhone(String name, String phone);

    // ID, 이름, 전화번호로 회원 검색
    Member findByMemberIdAndNameAndPhone(String memberId, String name, String phone);

    // 이름에 특정 문자열이 포함된 회원 목록 검색
    List<Member> findByNameContaining(String name);

    public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
        List<Inquiry> findByMemberId(String memberId);
}
}
