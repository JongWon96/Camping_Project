package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.config.auth.dto.SessionUser;
import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpSession;


@Controller
@SessionAttributes("loginUser")
public class MemberController {
	@Autowired
	private MemberService memberService;

	// 로그인 표시
	@GetMapping("/login_form")
	public String loginView() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

		return "member/login";
	}

	// 사용자 로그인
	@PostMapping("/login")
	public String loginAction(Member vo, Model model, HttpSession session) {
		String url = "";
		
		SessionUser user = (SessionUser) session.getAttribute("user");
		
		if (user != null) {
			model.addAttribute("userName", user.getName());
		}

		if (memberService.loginMemberId(vo) == 1) { // 정상 사용자
			// 사용자 정보를 페이지 내장 객체와 세션 내장객체에 저장
			model.addAttribute("loginUser", memberService.getMember(vo.getMemberId()));
			//model.addAttribute("userName", url)
			System.out.println("memberService.getMember(vo.getMemberId())" + memberService.getMember(vo.getMemberId()));

			url = "redirect:/main";
		} else {
			url = "member/login_fail";
		}

		return url;
	}
	
	// 로그아웃 처리
		@GetMapping("logout")
		public String logout(SessionStatus status) {
			
			status.setComplete();  // 세션 종료
			
			return "member/login";
		}

	// 약정화면 표시
	@GetMapping("/contract")
	public String contractView() {

		return "member/contract";
	}

	// 회원가입 화면 표시
	@GetMapping("/join_form")
	public String joinView() {

		return "member/join";
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
		return "member/idcheck";
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

		return "member/login";
	}

	// 아이디 찾기 화면 표시
	@GetMapping("/find_id_form")
	public String findIdView() {

		return "member/findId";
	}

	// 비밀번호 찾기 화면 표시
	@GetMapping("/find_pwd_form")
	public String findPwdView() {

		return "member/findPassword";
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

		return "member/findResult";
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

		return "member/findPwdResult";
	}
	/*
	 * 비밀번호 변경 처리
	 */
	@PostMapping("change_pwd")
	public String changePwd(Member vo) {
		memberService.changePassword(vo);

		return "member/changePwdOk";
	}
	
	@RequestMapping("member/address.do")
	public String adress() {
		return "member/join";
	}
	
}
