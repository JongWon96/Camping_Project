package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.domain.Camping;

public interface SearchService {
	
    Page<Camping> searchCampings(
        String donm,
        String sigungunm,
        String category,
        String campingName, // 캠핑장 이름 추가
        String flooring,
        String bonfire,
        String petAllowed,
        String trailerAllowed,
        String caravanAllowed,
		Pageable paging
    );
}
