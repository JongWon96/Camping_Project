package com.example.demo.config.auth;

import java.util.Collections;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.config.auth.dto.OAuthAttributes;
import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberService memberService;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기본 OAuth2UserService 호출
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 로그인한 서비스 구분
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuth2User에서 사용자 정보 추출
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 디버깅: 받은 attributes 출력
        System.out.println("OAuth2User attributes: " + oAuth2User.getAttributes());

        // 사용자 저장 또는 업데이트
        Member member = saveOrUpdate(attributes);

        // 세션에 사용자 정보 저장
        httpSession.setAttribute("loginUser", member);

        System.out.println("attribute=" + attributes.getAttributes());
        // 반환값
        return new DefaultOAuth2User(
                Collections.emptySet(),
                attributes.getAttributes(),
                userNameAttributeName);  // name attribute 설정
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        return memberService.saveOrUpdate(attributes.toMemberEntity());
    }
}
