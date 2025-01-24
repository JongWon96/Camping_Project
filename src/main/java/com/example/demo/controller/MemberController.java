package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;  // userService 대신 memberService 사용

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("loginUser")  // 세션에 "loginUser" 이름으로 저장
public class MemberController {

	@Autowired
	private MemberService memberService;  // userService 대신 memberService 사용

	// 로그인 표시//
	@GetMapping("/login")
	public String loginView() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		return "userinfo/login";
	}

	// 사용자 로그인
	@PostMapping("/login")
	public String loginAction(Member vo, Model model, HttpSession session) {
		String url;

		// 세션에서 "loginUser"를 가져옴
		Member user = (Member) session.getAttribute("loginUser");

		if (user != null) {
			// 이미 로그인된 사용자
			model.addAttribute("userName", user.getName());
			return "redirect:/landingpage";
		}

		// 사용자 로그인 검증
		if (memberService.loginMemberId(vo) == 1) { // 로그인 성공
			Member loginUser = memberService.getMember(vo.getMemberId()); // 사용자 정보 가져오기
			session.setAttribute("loginUser", loginUser); // 세션에 Member 저장
			model.addAttribute("userName", loginUser.getName());
			url = "redirect:/landingpage"; // 메인 페이지로 리다이렉트
		} else {
			url = "userinfo/login_fail"; // 로그인 실패 페이지
		}

		return url;
	}
	// 로그아웃 처리
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		status.setComplete();  // 세션 종료
		return "userinfo/login";
	}


	// 약정화면 표시
	@GetMapping("/contract")
	public String contractView() {

		return "userinfo/contract";
	}

	// 회원가입 화면 표시
	@GetMapping("/join")
	public String joinView() {

		return "userinfo/join";
	}


	// ID중복 확인 처리
	@GetMapping("/id_check_form")
	public String idCheckView(Member vo, Model model) {
		// confirmID()를 호출하여 id존재 확인 결과 저장
		// result 결과 1이면 id존재, -1이면 id존재하지 않음.
		int result = memberService.confirmMemberId(vo.getMemberId());

		// confirmID()의 결과를 model 객체에 저장
		model.addAttribute("message", result);
		model.addAttribute("memberId", vo.getMemberId());

		// idcheck 화면 호출
		return "userinfo/idcheck";
	}

	// 회원가입 처리
	@PostMapping("/join")
	public String joinAction(Member vo,
							 @RequestParam("detailAddress") String detailAddress,
							 @RequestParam("extraAddress") String extraAddress) {
		if (detailAddress != null) {
			vo.setAddress(vo.getAddress() + " " + detailAddress);
		}

		if (extraAddress != null) {
			vo.setAddress(vo.getAddress() + " " + extraAddress);
		}
		System.out.println("회원가입: vo=" + vo);
		memberService.insertMember(vo);

		return "userinfo/login";
	}

	// 아이디 찾기 화면 표시
	@GetMapping("/find_id_form")
	public String findIdView() {

		return "userinfo/findId";
	}

	// 비밀번호 찾기 화면 표시
	@GetMapping("/find_pwd_form")
	public String findPwdView() {

		return "userinfo/findPassword";
	}

	// 아이디 찾기 처리
	@PostMapping("/find_id")
	public String findIdAction(Member vo, Model model) {
		Member member = memberService.getMemberIdByNameAndPhone(vo.getName(), vo.getPhone());

		if(member != null) {  // 아이디 조회 성공
			model.addAttribute("message", 1);
			model.addAttribute("memberId", member.getMemberId());
		} else {
			model.addAttribute("message", -1);
		}

		return "userinfo/findResult";
	}


	// 비밀번호 찾기 처리
	@PostMapping("/find_pwd")
	public String findPwdAction(Member vo, Model model) {
		// 화면에서 입력한 id, name, phone을 조건으로 비밀번호 찾기 서비스 호출
		Member member = memberService.getPasswordByMemberIdNamePhone(vo.getMemberId(), vo.getName(), vo.getPhone());

		if(member != null) {  // 사용자 조회 성공
			model.addAttribute("message", 1);
			model.addAttribute("memberId", member.getMemberId());
		} else {
			model.addAttribute("message", -1);
		}

		return "userinfo/findPwdResult";
	}
	/*
	 * 비밀번호 변경 처리
	 */
	@PostMapping("change_pwd")
	public String changePwd(Member vo) {
		memberService.changePassword(vo);

		return "userinfo/changePwdOk";
	}

	@RequestMapping("member/address.do")
	public String adress() {
		return "userinfo/join";
	}

}




