package com.example.demo.service;

import com.example.demo.domain.Camping;

public interface CampingService {
    Camping findById(Long id);  // 반환 타입을 Optional에서 Camping으로 변경
}
