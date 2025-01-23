package com.example.demo.config.auth.dto;

import com.example.demo.domain.Member;
import lombok.Getter;

@Getter
public class SessionUser {
    private String name;
    private String email;
    private String googleId;  // 구글 고유 ID (sub)
    private String profilePicture;  // 구글 프로필 사진 URL
    private String memberId;  // 이메일을 memberId로 사용 (기존 코드는 이메일을 사용하고 있다고 가정)

    // 기존 Member 객체로부터 정보 가져오는 생성자
    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.memberId = member.getEmail();  // 이메일을 memberId로 사용
    }

}
