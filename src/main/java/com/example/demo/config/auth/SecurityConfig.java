package com.example.demo.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrfConfig -> csrfConfig.disable())  // CSRF 비활성화
            .headers(headerConfig -> headerConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))  // 프레임 옵션 비활성화
            .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                .anyRequest().permitAll()  // 모든 요청에 대해 인증 없이 접근 가능
            )
            .logout(logout -> logout.logoutSuccessUrl("/landingpage"))  // 로그아웃 후 리다이렉트 URL 설정
            // OAuth2 로그인 설정
            .oauth2Login(oauth -> oauth
                    .loginPage("/login")
                .userInfoEndpoint(endpoint -> endpoint.userService(customOAuth2UserService))  // 사용자 정보 처리 담당 Service 설정
                .defaultSuccessUrl("/landingpage", true)  // 로그인 성공 후 이동할 페이지 설정
            );

        return http.build();
    }
}
