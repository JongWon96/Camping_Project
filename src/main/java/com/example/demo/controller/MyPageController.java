package com.example.demo.controller;

import com.example.demo.domain.Inquiry;
import com.example.demo.domain.Member;
import com.example.demo.persistence.InquiryRepository;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private MemberService memberService;

    // 공통된 사용자 세션 체크 메소드
    private Member getMemberFromSession(HttpSession session, RedirectAttributes redirectAttributes) {
        Member member = (Member) session.getAttribute("loginUser");
        if (member == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 해주세요.");
        }
        return member;
    }

    // 마이페이지 메인
    @GetMapping
    public String myPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Member member = getMemberFromSession(session, redirectAttributes);
        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member); // 변수명을 "member"로 전달
        return "mypage"; // mypage.html
    }
    // 사용자 정보 수정 페이지
    @GetMapping("/edit-info")
    public String editInfoPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Member member = getMemberFromSession(session, redirectAttributes);
        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);
        return "mypage/edit-info";
    }

    // 사용자 정보 수정 처리
    @PostMapping("/update")
    public String updateMemberInfo(
            @SessionAttribute("loginUser") Member member,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "email", required = false) String email,


            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        try {
            Member updatedMember = memberService.updateMemberInfo(
                    member.getMemberId(),
                    phone != null ? phone : member.getPhone(),
                    address!= null ? address : member.getAddress(),
                    email!= null ? email : member.getEmail()

            );
            session.setAttribute("loginUser", updatedMember);

            redirectAttributes.addFlashAttribute("successMessage", "정보가 성공적으로 수정되었습니다.");
            return "redirect:/mypage";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "정보 수정 중 오류가 발생했습니다.");
            return "redirect:/mypage";
        }
    }

    // 비밀번호 변경 페이지
    @GetMapping("/changepassword")
    public String changePasswordPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Member member = getMemberFromSession(session, redirectAttributes);
        if (member == null) return "redirect:/login";

        model.addAttribute("member", member);
        return "mypage/changepassword";
    }

    // 비밀번호 변경 처리
    @PostMapping("/changepassword")
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        Member member = (Member) session.getAttribute("loginUser");
        if (member == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        try {
            memberService.changePassword(member.getMemberId(), currentPassword, newPassword, confirmPassword);
            redirectAttributes.addFlashAttribute("successMessage", "비밀번호가 성공적으로 변경되었습니다.");
            return "redirect:/mypage";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/mypage/changepassword";
        }
    }

    // 회원 탈퇴 처리
    @PostMapping("/delete")
    public String deleteMember(HttpSession session, RedirectAttributes redirectAttributes) {
        Member member = (Member) session.getAttribute("loginUser");

        if (member == null) {
            return "redirect:/login";
        }

        try {
            memberService.deleteMemberById(member.getMemberId());
            session.invalidate(); // 세션 무효화 (로그아웃 처리)

            redirectAttributes.addFlashAttribute("successMessage", "회원 탈퇴가 완료되었습니다.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "회원 탈퇴 중 오류가 발생했습니다.");
            return "redirect:/mypage";
        }
    }

    // 1:1 문의 내역 조회
    @GetMapping("/Inquiry")
    public String getInquiries(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Member member = getMemberFromSession(session, redirectAttributes);
        if (member == null) {
            return "redirect:/login";
        }

        List<Inquiry> inquiry = memberService.getInquiriesByMember(member);
        model.addAttribute("Inquiry", inquiry);

        return "mypage/inquiry";
    }

    // 1:1 문의 상세 페이지
    @GetMapping("/inquiry/detail/{id}")
    public String getInquiryDetail(@PathVariable Long id, Model model) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 문의를 찾을 수 없습니다."));
        model.addAttribute("inquiry", inquiry);

        return "mypage/InquiryDetail";
    }

    // 새 문의하기 폼 페이지
    @GetMapping("/inquiry/new")
    public String showInquiryForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Member member = getMemberFromSession(session, redirectAttributes);
        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member); // 추가 정보가 필요하다면 설정
        return "mypage/NewInquiry"; // 새 문의하기 폼 페이지 템플릿
    }



    // 1:1 문의 등록 요청 처리
    @PostMapping("/inquiry")
    public String submitInquiry(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "img", required = false) MultipartFile imgFile,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        Member member = (Member) session.getAttribute("loginUser");
        if (member == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        try {
            memberService.submitInquiry(member.getMemberId(), title, content, imgFile);
            redirectAttributes.addFlashAttribute("successMessage", "문의가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "문의 등록 중 오류가 발생했습니다.");
        }

        return "redirect:/mypage/Inquiry";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session , RedirectAttributes redirectAttributes) {
        // 세션 무효화
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "로그아웃이 성공적으로 완료되었습니다.");

        return "redirect:/login";
    }
}


