package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.example.demo.domain.Camping;

public interface SearchService {
    Page<Camping> searchCampings(
        String donm,
        String sigungunm,
        String category,
        String campingName, // 캠핑장 이름 추가
        String flooring,
        LocalDate startDate,
        LocalDate endDate,
        String bonfire,
        String petAllowed,
        String trailerAllowed,
        String caravanAllowed,
		int page, int size
    );
}
