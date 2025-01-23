package com.example.demo.persistence;

import com.example.demo.domain.Inquiry;
import com.example.demo.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByMember(Member member);
 // 사용자별 문의사항 목록 조회 (JPQL 사용)
 	@Query("SELECT q FROM Inquiry q WHERE q.member.id=?1 ORDER BY q.createddate DESC")
 	public List<Inquiry> getInquiryList(Long id);
}

