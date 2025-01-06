package com.example.demo.service;

import com.example.demo.domain.Member;

public interface MemberService {
    Member findByUsername(String memberId);
}
	