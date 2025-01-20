package com.example.demo.service;

import com.example.demo.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MemberService {
	    
    // 회원 로그인
    // 리턴값: 1 - ID, 0: Pwd 불일치, -1: ID가 존재하지 않음.
    public int loginMemberId(Member vo);

    // 회원 ID 확인
    // id가 존재하면 1, 존재하지 않으면 -1
    public int confirmMemberId(String memberId);
    
    // 회원정보 상세 조회
    public Member getMember(String memberId);

    // 회원정보 저장
    public void insertMember(Member vo);

    // Name과 Phone으로 id 찾기
    public Member getMemberIdByNameAndPhone(String name, String phone);

    // Id와 Name과 Phone으로 pw 찾기
    public Member getPasswordByMemberIdNamePhone(String memberId, String name, String phone);

    public void changePassword(Member vo);

    // 전체 회원 조회
    public List<Member> getMemberList(String name);
}
