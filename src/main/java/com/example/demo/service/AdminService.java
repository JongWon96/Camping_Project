package com.example.demo.service;

import com.example.demo.domain.Admin;

public interface AdminService {

	/*
	 * 관리자 로그인: adminCheck()
	 * 리턴값: -1 - id가 존재하지 않음
	 *        0 - 비밀번호 맞지 않음
	 *        1 - 정상 로그인
	 */
	int adminCheck(Admin vo);

	/*
	 * 관리자 정보 조회
	 */
	Admin getAdmin(Long id);

}