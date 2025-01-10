package com.example.demo.service;


import com.example.demo.domain.Inquiry;
import com.example.demo.domain.Member;
import com.example.demo.persistence.InquiryRepository;
import com.example.demo.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;


    @Autowired
    private InquiryRepository inquiryRepository;
    
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> findByUsername(String memberId) {
        return memberRepository.findByMemberId(memberId);
    }
    
    
    @Transactional
    @Override
    public Member updateMemberInfo(String memberId, String phone) {
        Member member = memberRepository.findMemberByMemberId(memberId);
        if (member == null) {
            throw new RuntimeException("수정할 회원을 찾을 수 없습니다.");
        }
        member.setPhone(phone);
        return memberRepository.save(member);
    }

    @Override
    public Member updateMemberInfo(String memberId, String phone, String address, String email) {
        return null;
    }

    @Transactional
    @Override
    public void changePassword(String memberId, String currentPassword, String newPassword, String confirmPassword) {
        Member member = memberRepository.findMemberByMemberId(memberId);
        if (member == null) {
            throw new RuntimeException("회원 정보를 찾을 수 없습니다.");
        }

        if (!member.getPassword().equals(currentPassword)) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        member.setPassword(newPassword);
        memberRepository.save(member);
    }

    @Override
    public void deleteMemberById(String memberId) {
        Member member = memberRepository.findMemberByMemberId(memberId);
        if (member == null) {
            throw new RuntimeException("삭제할 회원을 찾을 수 없습니다.");
        }
        memberRepository.delete(member);
    }

    @Override
    public int login(String memberId, String password) {
        Member member = memberRepository.findMemberByMemberId(memberId);
        if (member == null) {
            return -1; // ID가 존재하지 않음
        }
        if (member.getPassword().equals(password)) {
            return 1; // 로그인 성공
        }
        return 0; // 비밀번호 불일치
    }

    @Override
    public boolean isMemberIdExists(String memberId) {
        return memberRepository.findMemberByMemberId(memberId) != null;
    }

    @Override
    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMemberByNameAndPhone(String name, String phone) {
        Member member = memberRepository.findByNameAndPhone(name, phone);
        if (member == null) {
            throw new RuntimeException("해당 이름과 전화번호로 회원을 찾을 수 없습니다.");
        }
        return member;
    }

    @Override
    public Member findMemberByIdNameAndPhone(String memberId, String name, String phone) {
        Member member = memberRepository.findByMemberIdAndNameAndPhone(memberId, name, phone);
        if (member == null) {
            throw new RuntimeException("해당 ID, 이름, 전화번호로 회원을 찾을 수 없습니다.");
        }
        return member;
    }

    @Override
    public List<Member> findMembersByName(String name) {
        return memberRepository.findByNameContaining(name);
    }

    @Override
    public Member getMemberId(String memberId) {
        Member member = memberRepository.findMemberByMemberId(memberId);
        if (member == null) {
            throw new RuntimeException("해당 ID로 회원을 찾을 수 없습니다.");
        }
        return member;
    }

    @Override
    public List<Inquiry> getInquiriesByMember(Member member) {
        return inquiryRepository.findByMember(member); // Member 객체를 기준으로 조회
    }


    @Value("${file.upload-dir}")
    private String uploadDir;
    public void submitInquiry(String memberId, String title, String content, MultipartFile imgFile) throws IOException {
        Member member = memberRepository.findMemberByMemberId(memberId);
        if (member == null) {
            throw new RuntimeException("해당 회원을 찾을 수 없습니다.");
        }

        Inquiry inquiry = new Inquiry();
        inquiry.setMember(member);
        inquiry.setTitle(title);
        inquiry.setContent(content);
        inquiry.setStatus(0); // 상태 설정: 0 = 대기 중
        inquiry.setCreateddate(LocalDateTime.now());

        // 이미지 처리
        if (imgFile != null && !imgFile.isEmpty()) {
            String imgPath = saveImage(imgFile); // 이미지 저장 후 경로 반환
            inquiry.setImg(imgPath); // DB에 저장될 경로 설정
        }

        inquiryRepository.save(inquiry); // DB에 저장
    }

    private String saveImage(MultipartFile imgFile) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + imgFile.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);

        Files.createDirectories(path.getParent()); // 디렉토리가 없으면 생성
        Files.write(path, imgFile.getBytes()); // 파일 저장

        return "/uploads/" + fileName; // 저장된 파일 경로 반환
    }
    }


