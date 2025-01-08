package com.example.demo.service;

import com.example.demo.domain.Camping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void testSearchCampingsWithComplexCriteria() {
        // 복합 검색 조건 설정
        String donm = "서울특별시";
        String sigungunm = "강남구";
        String category = "글램핑";
        String flooring = "잔디";
        String bonfire = "화로대";
        String petAllowed = "가능";
        String trailerAllowed = "Y";
        String caravanAllowed = "N";

        // Service 호출
        List<Camping> results = searchService.searchCampings(
            donm,         // 도
            sigungunm,    // 시군구
            category,     // 카테고리
            flooring,     // 바닥재
            null,         // 시작 날짜
            null,         // 끝 날짜
            bonfire,      // 불멍 여부
            petAllowed,   // 반려동물
            trailerAllowed, // 개인 트레일러
            caravanAllowed  // 개인 카라반
        );

        // 결과 출력
        System.out.println("검색 조건:");
        System.out.println(" - 도: " + donm);
        System.out.println(" - 시군구: " + sigungunm);
        System.out.println(" - 카테고리: " + category);
        System.out.println(" - 바닥재: " + flooring);
        System.out.println(" - 불멍 여부: " + bonfire);
        System.out.println(" - 반려동물: " + petAllowed);
        System.out.println(" - 개인 트레일러: " + trailerAllowed);
        System.out.println(" - 개인 카라반: " + caravanAllowed);

        System.out.println("\n검색 결과:");
        for (Camping camping : results) {
            System.out.println(" - 캠핑장 이름: " + camping.getFacltnm());
            System.out.println(" - 도: " + camping.getDonm());
            System.out.println(" - 시군구: " + camping.getSigungunm());
            System.out.println(" - 카테고리: " + camping.getCategory());
            System.out.println(" - 바닥재: " + camping.getSitebottomcl1());
        }

        // 결과 검증 (결과가 비어 있지 않아야 함)
        assertFalse(results.isEmpty(), "검색 결과가 비어 있어서는 안 됩니다.");
    }
}
