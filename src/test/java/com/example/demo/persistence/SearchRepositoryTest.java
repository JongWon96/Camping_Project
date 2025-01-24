package com.example.demo.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.domain.Camping;

@SpringBootTest
public class SearchRepositoryTest {

    @Autowired
    private SearchRepository searchRepository;

    @Test
    @Disabled
    public void testSearchCampingsByCampingName() {
        // Given
        String campingName = "힐링";
        Pageable pageable = PageRequest.of(0, 10); // 페이지 크기: 10개

        // When
        /*
        Page<Camping> result = searchRepository.searchCampings(
            null, // donm
            null, // sigungunm
            null, // category
            campingName, // campingName
            null, // flooring
            null, // startDate
            null, // endDate
            null, // bonfire
            null, // petAllowed
            null, // trailerAllowed
            null, // caravanAllowed
            pageable
        );

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isGreaterThan(0); // 검색 결과가 있어야 함

        result.forEach(camping -> {
            System.out.println("Found camping: " + camping.getFacltnm());
            assertThat(camping.getFacltnm()).containsIgnoringCase(campingName);
        });
        */
    }
    
    @Test
    @Disabled
    public void testSearchByDonmAndSigungunm() {
        // Arrange: 테스트 데이터를 준비합니다.
        String donm = "경상북도";
        String sigungunm = "청송군";
        Pageable pageable = PageRequest.of(0, 10); // 첫 페이지, 10개씩 표시

        // Act: Repository의 검색 메서드를 호출합니다.
        /*
        Page<Camping> result = searchRepository.searchCampings(
                donm,
                sigungunm,
                null, // category
                null, // campingName
                null, // flooring
                null, // startDate
                null, // endDate
                null, // bonfire
                null, // petAllowed
                null, // trailerAllowed
                null, // caravanAllowed,
                pageable
        );

        // Assert: 결과를 검증합니다.
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isGreaterThan(0);

        // 결과 확인을 위해 출력
        result.getContent().forEach(camping -> {
            System.out.println("Camping Name: " + camping.getFacltnm());
            System.out.println("Donm: " + camping.getDonm());
            System.out.println("Sigungunm: " + camping.getSigungunm());
        });
        */
    }
}
