package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Admin;
import com.example.demo.persistence.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepo;
	
	/*
	 * 관리자 로그인: adminCheck()
	 * 리턴값: -1 - id가 존재하지 않음
	 *        0 - 비밀번호 맞지 않음
	 *        1 - 정상 로그인
	 */
	@Override
	public int adminCheck(Admin vo) {
		int result = -1;	// 로그인 결과 저장
		
		// admin 테이블에서 관리자 정보 조회
		Optional<Admin> admin = adminRepo.findById(vo.getId());
		
		if (admin.isEmpty()) {
			result = -1;
		} else if(vo.getPwd().equals(admin.get().getPwd())) {
			result = 1;
		} else {
			result = 0;
		}
		
		return result;
	}
	
	/*
	 * 관리자 정보 조회
	 */
	@Override
	public Admin getAdmin(Long id) {
		
		return adminRepo.findById(id).get();
	}
}










