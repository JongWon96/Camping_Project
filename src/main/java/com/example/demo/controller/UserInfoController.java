package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("loginUser")
public class UserInfoController {

	@Autowired
	private MemberService memberService;

	// 로그인 페이지 표시
	@GetMapping("/login")
	public String loginView() {
		return "userinfo/login";
	}

	// 사용자 로그인 처리
	@PostMapping("/login")
	public String loginAction(@ModelAttribute Member member, Model model) {
		System.out.println("로그인 시도 - memberId: " + member.getMemberId());
		System.out.println("로그인 시도 - password: " + member.getPassword());

		int loginResult = memberService.login(member.getMemberId(), member.getPassword());
		if (loginResult == 1) {
			Member loginUser = memberService.getMemberId(member.getMemberId());
			model.addAttribute("loginUser", loginUser);
			return "redirect:/mypage";
		} else {
			return "userinfo/login_fail";
		}
	}

	// 약관 페이지 표시
	@GetMapping("/contract")
	public String contractView() {
		return "userinfo/contract";
	}

	// 회원가입 페이지 표시
	@GetMapping("/join_form")
	public String joinView() {
		return "userinfo/join";
	}

	// ID 중복 확인 처리
	@GetMapping("/id_check_form")
	public String idCheckView(@RequestParam("memberId") String memberId, Model model) {
		boolean exists = memberService.isMemberIdExists(memberId);
		model.addAttribute("message", exists ? 1 : -1);
		model.addAttribute("memberId", memberId);

		return "userinfo/idcheck";
	}

	// 회원가입 처리
	@PostMapping("/join")
	public String joinAction(Member member) {
		memberService.saveMember(member);
		return "userinfo/login";
	}

	// 아이디 찾기 페이지 표시
	@GetMapping("/find_id_form")
	public String findIdView() {
		return "userinfo/findId";
	}

	// 비밀번호 찾기 페이지 표시
	@GetMapping("/find_pwd_form")
	public String findPwdView() {
		return "userinfo/findPassword";
	}

	// 아이디 찾기 처리
	@PostMapping("/find_id")
	public String findIdAction(
			@RequestParam("name") String name,
			@RequestParam("phone") String phone,
			Model model
	) {
		Member member = memberService.findMemberByNameAndPhone(name, phone);

		if (member != null) {
			model.addAttribute("message", 1);
			model.addAttribute("memberId", member.getMemberId());
		} else {
			model.addAttribute("message", -1);
		}

		return "userinfo/findResult";
	}

	// 비밀번호 찾기 처리
	@PostMapping("/find_pwd")
	public String findPwdAction(
			@RequestParam("memberId") String memberId,
			@RequestParam("name") String name,
			@RequestParam("phone") String phone,
			Model model
	) {
		Member member = memberService.findMemberByIdNameAndPhone(memberId, name, phone);

		if (member != null) {
			model.addAttribute("message", 1);
			model.addAttribute("password", member.getPassword());
		} else {
			model.addAttribute("message", -1);
		}

		return "userinfo/findPwdResult";
	}

	// 비밀번호 변경 처리
	@PostMapping("/change_pwd")
	public String changePwd(
			@RequestParam("memberId") String memberId,
			@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword
	) {
		memberService.changePassword(memberId, currentPassword, newPassword, newPassword);
		return "userinfo/changePwdOk";
	}
}
