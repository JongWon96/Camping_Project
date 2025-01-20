package com.example.demo.config.auth.dto;

import java.io.Serializable;

import com.example.demo.domain.User;

import lombok.Data;

@Data
public class SessionUser implements Serializable {
    // 인증된 사용자 정보만 필요 => name, email, picture 필드만 선언
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
