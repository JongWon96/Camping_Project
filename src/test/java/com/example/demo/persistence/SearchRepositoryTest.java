package com.example.demo.persistence;

import com.example.demo.domain.Camping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SearchRepositoryTest {

    @Autowired
    private SearchRepository searchRepository;


    @Test
    void testSearchCampingsWithFilters() {
        // Given: 복합 조건
        String donm = "강원도";
        String sigungunm = "고성군";
        String category = "카라반";
        String flooring = "잔디";
        String bonfire = "화로대";
        String petAllowed = "가능";
        String trailerAllowed = "Y";
        String caravanAllowed = "N";

        // When: 조건 검색
        List<Camping> campings = searchRepository.searchCampings(
                donm, sigungunm, category, flooring, null, null, bonfire,
                petAllowed, trailerAllowed, caravanAllowed);

        // Then: 결과 검증
        assertThat(campings).isNotEmpty();

        // Print 검색 결과
        System.out.println("=== 검색 결과 (복합 조건) ===");
        campings.forEach(c -> {
            System.out.println(" - 캠핑장 이름: " + c.getFacltnm());
            System.out.println("   도: " + c.getDonm() + ", 시군구: " + c.getSigungunm());
            System.out.println("   카테고리: " + c.getCategory());
        });
    }
}
