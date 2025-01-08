package com.example.demo.service;

import com.example.demo.domain.Camping;

import java.time.LocalDate;
import java.util.List;

public interface SearchService {
    List<Camping> searchCampings(
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
        String caravanAllowed
    );
}
