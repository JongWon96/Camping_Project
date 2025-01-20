package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Member;
import com.example.demo.persistence.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepo;

	// 회원 로그인
	// 리턴값: 1 - ID, Pw 일치, 0: Pw 불일치, -1: ID가 존재하지 않음.
	@Override
	public int loginMemberId(Member vo) {
		int result = -1;
		
		Member member = memberRepo.findByMemberId(vo.getMemberId());
		
		if(member == null) {
			result = -1;
		} else if(vo.getPassword().equals(member.getPassword())) {
			result = 1;	// id, pw가 모두 일치	
		} else {
			result = 0; // 비밀번호 불일치			
		}
		
		return result;
	}

	// 회원 ID 확인
	// id가 존재하면 1, 존재하지 않으면 -1
	@Override
	public int confirmMemberId(String memberId) {
		Member member = memberRepo.findByMemberId(memberId);
			
			if(member == null) {
				return -1;
			} else {
				return 1;
			}
	}
	
	// 회원정보 상세 조회
	@Override
	public Member getMember(String memberId) {
		return memberRepo.findByMemberId(memberId);
	}

	@Override
	public void insertMember(Member vo) {
		memberRepo.save(vo);
	}

	@Override
	public Member getMemberIdByNameAndPhone(String name, String phone) {
		return memberRepo.findByNameAndPhone(name, phone);
	}

	@Override
	public Member getPasswordByMemberIdNamePhone(String memberId, String name, String phone) {
		return memberRepo.findByMemberIdAndNameAndPhone(memberId, name, phone);
	}

	@Override
	public void changePassword(Member vo) {
		System.out.println("memberId="+vo.getMemberId());
		System.out.println("password="+vo.getPassword());
		memberRepo.changePassword(vo.getMemberId(), vo.getPassword());
	}

	@Override
	public List<Member> getMemberList(String name) {
		return memberRepo.getMemberList(name);
	}

}
