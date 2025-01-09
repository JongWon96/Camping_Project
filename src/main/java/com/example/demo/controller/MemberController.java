package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.Member;

import jakarta.servlet.http.HttpSession;

public class MemberController {

	
	@GetMapping("/testSession")
	public String testSession(HttpSession session, Model model) {
	    // Member 객체 생성 (테스트용 데이터)
	    Member mockMember = new Member();
	    mockMember.setMemberId("testUser123");
	    mockMember.setName("홍길동");
	    mockMember.setPhone("010-1234-5678");
	    mockMember.setGender(1); // 1 = 남성
	    mockMember.setAge(30);
	    mockMember.setEmail("testuser@example.com");
	    mockMember.setAddress("서울시 강남구");

	    // 세션에 'member'로 저장
	    session.setAttribute("member", mockMember);

	    // 모델에 사용자 이름과 다른 정보를 추가
	    model.addAttribute("memberName", mockMember.getName());

	    return "reservationPage";  // 'reservationPage.html'로 이동
	}

}
