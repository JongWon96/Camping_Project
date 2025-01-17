package com.example.demo.service;

import com.example.demo.domain.Inquiry;
import com.example.demo.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface MemberService {

    Member updateMemberInfo(String memberId, String phone, String address, String email);

    void changePassword(String memberId, String currentPassword, String newPassword, String confirmPassword);

    void deleteMemberById(String memberId);

    int login(String memberId, String password);

    boolean isMemberIdExists(String memberId);

    void saveMember(Member member);

    Member findMemberByNameAndPhone(String name, String phone);

    Member findMemberByIdNameAndPhone(String memberId, String name, String phone);

    List<Member> findMembersByName(String name);

    Member getMemberId(String memberId);

    // 특정 회원의 문의 내역 조회 (Member를 기준으로)
    List<Inquiry> getInquiriesByMember(Member member);

    // 문의 등록
    void submitInquiry(String memberId, String title, String content, MultipartFile img) throws IOException;
    
    List<Member> getMemberList(String name);
}
