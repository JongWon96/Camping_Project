package com.example.demo.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Camping;

@SpringBootTest
class SearchRepositoryTest {

    @Autowired
    private SearchRepository searchRepository;

    @Test
    void testSearchCampingsByCampingName() {
        // Given: 캠핑장 이름 조건
        String campingName = "힐링";

        // When: 캠핑장 이름으로 검색
        List<Camping> campings = searchRepository.searchCampings(
                null, null, null, campingName, null,
                null, null, null, null, null, null);

        // Then: 결과 검증
        assertThat(campings).isNotEmpty();

        // Print 검색 결과
        System.out.println("=== 검색 결과 (캠핑장 이름 기준: " + campingName + ") ===");
        campings.forEach(c -> {
            System.out.println(" - 캠핑장 이름: " + c.getFacltnm());
            System.out.println("   도: " + c.getDonm() + ", 시군구: " + c.getSigungunm());
        });
    }

    @Test
    @Disabled
    void testSearchCampingsWithMultipleFilters() {
        // Given: 복합 조건
        String donm = "강원도";
        String sigungunm = "고성군";
        String category = "카라반";
        String campingName = "힐링";
        String flooring = "잔디";
        String bonfire = "화로대";
        String petAllowed = "가능";
        String trailerAllowed = "Y";
        String caravanAllowed = "N";

        // When: 조건 검색
        List<Camping> campings = searchRepository.searchCampings(
                donm, sigungunm, category, campingName, flooring, null, null,
                bonfire, petAllowed, trailerAllowed, caravanAllowed);

        // Then: 결과 검증
        assertThat(campings).isNotEmpty();

        // Print 검색 결과
        System.out.println("=== 검색 결과 (복합 조건) ===");
        campings.forEach(c -> {
            System.out.println(" - 캠핑장 이름: " + c.getFacltnm());
            System.out.println("   도: " + c.getDonm() + ", 시군구: " + c.getSigungunm());
            System.out.println("   카테고리: " + c.getCategory());
            System.out.println("   바닥재: " + flooring);
        });
    }

    @Test
    @Disabled
    void testSearchCampingsByDateRange() {
        // Given: 날짜 범위
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        // When: 날짜로 검색
        List<Camping> campings = searchRepository.searchCampings(
                null, null, null, null, null, startDate, endDate,
                null, null, null, null);

        // Then: 결과 검증
        assertThat(campings).isNotEmpty();

        // Print 검색 결과
        System.out.println("=== 검색 결과 (날짜 범위: " + startDate + " ~ " + endDate + ") ===");
        campings.forEach(c -> {
            System.out.println(" - 캠핑장 이름: " + c.getFacltnm());
            System.out.println("   예약 시작일: " + c.getHvofbgnde() + ", 예약 종료일: " + c.getHvofenddle());
        });
    }
}
