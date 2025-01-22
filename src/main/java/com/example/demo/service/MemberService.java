package com.example.demo.service;

import com.example.demo.config.auth.dto.SessionUser;
import com.example.demo.domain.Inquiry;
import com.example.demo.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface MemberService {


    @Transactional
    Member updateMemberInfo(String memberId, String phone);

    Member updateMemberInfo(String memberId, String phone, String address, String email);

    void changePassword(String memberId, String currentPassword, String newPassword, String confirmPassword);

    void deleteMemberById(String memberId);


    boolean isMemberIdExists(String memberId);

    void saveMember(Member member);

    // 회원 로그인
    public int loginMemberId(Member vo);

    // 회원 ID 확인
    public int confirmMemberId(String memberId);

    // 회원정보 상세 조회
    public Member getMember(String memberId);

    // 회원정보 저장
    public void insertMember(Member vo);

    // Name과 Phone으로 id 찾기
    public Member getMemberIdByNameAndPhone(String name, String phone);

    // Id와 Name과 Phone으로 pw 찾기
    public Member getPasswordByMemberIdNamePhone(String memberId, String name, String phone);

    // 비밀번호 변경
    public void changePassword(Member vo);

    // 전체 회원 조회
    public List<Member> getMemberList(String name);

    // 구글 로그인 시 회원 저장 또는 업데이트
    public Member saveOrUpdate(Member member);

    // 이메일로 회원 조회 (OAuth2 로그인 후 사용)
    public Member getMemberByEmail(String email);



    Member findMemberByNameAndPhone(String name, String phone);

    Member findMemberByIdNameAndPhone(String memberId, String name, String phone);

    List<Member> findMembersByName(String name);

    Member getMemberId(String memberId);

    // 특정 회원의 문의 내역 조회 (Member를 기준으로)
    List<Inquiry> getInquiriesByMember(Member member);

    // 문의 등록
    void submitInquiry(String memberId, String title, String content, MultipartFile img) throws IOException;


}
