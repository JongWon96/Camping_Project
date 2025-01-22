package com.example.demo.config.auth.dto;

import com.example.demo.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class OAuthAttributes {
  
    private String name;
    private String email;

    // 구글 로그인 후 받은 정보들
    private String googleId;  // 구글 고유 ID (sub)
    private String profilePicture;  // 구글 프로필 사진 URL
    
    // 추가 필드 (필요에 따라 사용)
    private String memberId;   // memberId (email 사용)
    private String password;    // password (null 또는 빈 값)
    private String phone;       // phone (빈 값)
    private Integer gender;     // gender (기본값 0)
    private Integer age;        // age (기본값 0)
    private String address;     // address (빈 값)

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        throw new IllegalArgumentException("지원하지 않는 소셜 로그인입니다.");
    }

    // 구글 로그인 처리
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        String googleId = (String) attributes.get("sub");
        if (googleId == null) {
            throw new IllegalArgumentException("Google login error: 'sub' attribute ssscannot be null");
        }

        return OAuthAttributes.builder()
                .googleId(googleId)  // Google의 경우 sub를 ID로 사용
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profilePicture((String) attributes.get("picture"))  // 프로필 사진 URL
                .build();
    }

    public Map<String, Object> getAttributes() {
        // attributes 반환
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", this.googleId);  // 구글 고유 ID (sub)
        attributes.put("name", this.name);
        attributes.put("email", this.email);
        attributes.put("profilePicture", this.profilePicture);  // 구글 프로필 사진 URL
        return attributes;
    }

    // DB에는 googleId, profilePicture를 저장하지 않음
    public Member toMemberEntity() {
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .memberId(this.email)  // 이메일을 memberId로 사용
                .password("1234")  // 기본 비밀번호 설정
                .phone("010-1111-1111")  // 전화번호 기본값 설정
                .gender(0)  // 기본값
                .age(0)  // 기본값
                .address("UNKNOWN")  // 기본값
                .build();
    }
}
