package com.example.demo.service;

import com.example.demo.domain.Camping;
import com.example.demo.persistence.SearchRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchServiceTest {

    @Autowired
    private SearchService searchService; // SearchServiceImpl 테스트 대상

    @Test
    void searchCampings() {
        // 테스트 데이터 입력
        String donm = "충청남도";
        String sigungunm = "태안군";
        //String category = "일반 캠핑";
        //String campingName = "우니메이카 태안점";
        //String flooring = "데크";
        //LocalDate startDate = LocalDate.of(2025, 1, 10); // 예약 시작 날짜
        //LocalDate endDate = LocalDate.of(2025, 1, 20);   // 예약 종료 날짜
        //String bonfire = "Y";
        //String petAllowed = "가능";
        //String trailerAllowed = "N";
        //String caravanAllowed = "N";

        // 실제 서비스 호출
        List<Camping> results = searchService.searchCampings(
            donm,
            sigungunm,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );

        // 결과 출력
        System.out.println("=== 검색 조건 ===");
        System.out.println("도: " + donm);
        System.out.println("시군구: " + sigungunm);
        //System.out.println("카테고리: " + category);
        //System.out.println("캠핑장 이름: " + campingName);
        //System.out.println("바닥재: " + flooring);
        //System.out.println("시작 날짜: " + startDate);
        //System.out.println("종료 날짜: " + endDate);
        //System.out.println("불멍 여부: " + bonfire);
        //System.out.println("반려동물 가능 여부: " + petAllowed);
        //System.out.println("트레일러 가능 여부: " + trailerAllowed);
        //System.out.println("카라반 가능 여부: " + caravanAllowed);
        System.out.println();

        System.out.println("=== 검색 결과 ===");
        results.forEach(camping -> {
            System.out.println("캠핑장 이름: " + camping.getFacltnm());
            System.out.println("도: " + camping.getDonm());
            System.out.println("시군구: " + camping.getSigungunm());
            System.out.println("카테고리: " + camping.getCategory());
            System.out.println("잔디 여부: " + camping.getSitebottomcl1());
            System.out.println("불멍 여부: " + camping.getEqpmnlendcl());
            System.out.println("반려동물 가능 여부: " + camping.getAnimalcmgcl());
            System.out.println("트레일러 가능 여부: " + camping.getTrleracmpnyat());
            System.out.println("카라반 가능 여부: " + camping.getCaravacmpnyat());
            System.out.println();
        });

        // 결과 검증
        assertNotNull(results, "검색 결과는 null이 아니어야 합니다.");
        assertFalse(results.isEmpty(), "검색 결과가 있어야 합니다.");

        // 특정 캠핑장 이름이 포함되어 있는지 확인
        boolean containsCampingName = results.stream()
            .anyMatch(camping -> camping.getFacltnm().contains(campingName));
        assertTrue(containsCampingName, "검색 결과에 캠핑장이 포함되어야 합니다.");
    }
}
