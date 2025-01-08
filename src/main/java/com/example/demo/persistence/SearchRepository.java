package com.example.demo.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.Camping;

public interface SearchRepository extends JpaRepository<Camping, Long> {

	@Query("SELECT c FROM Camping c WHERE " +
		       "(:donm IS NULL OR c.donm = :donm) AND " +
		       "(:sigungunm IS NULL OR c.sigungunm = :sigungunm) AND " +
		       "(:category IS NULL OR c.category = :category) AND " +
		       "(:campingName IS NULL OR c.facltNm LIKE %:campingName%) AND " + // 캠핑장 이름 검색 조건 추가
		       "(:flooring IS NULL OR " +
		       " (CASE :flooring " +
		       "     WHEN '잔디' THEN c.sitebottomcl1 " +
		       "     WHEN '파쇄석' THEN c.sitebottomcl2 " +
		       "     WHEN '데크' THEN c.sitebottomcl3 " +
		       "     WHEN '자갈' THEN c.sitebottomcl4 " +
		       "     WHEN '맨흙' THEN c.sitebottomcl5 " +
		       "     ELSE NULL END) <> '0') AND " +
		       "(:startDate IS NULL OR c.hvofbgnde <= :startDate) AND " +
		       "(:endDate IS NULL OR c.hvofenddle >= :endDate) AND " +
		       "(:bonfire IS NULL OR c.eqpmnlendcl LIKE %:bonfire%) AND " +
		       "(:petAllowed IS NULL OR c.animalcmgcl = :petAllowed) AND " +
		       "(:trailerAllowed IS NULL OR c.trleracmpnyat = :trailerAllowed) AND " +
		       "(:caravanAllowed IS NULL OR c.caravacmpnyat = :caravanAllowed)")
		List<Camping> searchCampings(
		    @Param("donm") String donm,
		    @Param("sigungunm") String sigungunm,
		    @Param("category") String category,
		    @Param("campingName") String campingName, // 캠핑장 이름 추가
		    @Param("flooring") String flooring,
		    @Param("startDate") LocalDate startDate,
		    @Param("endDate") LocalDate endDate,
		    @Param("bonfire") String bonfire,
		    @Param("petAllowed") String petAllowed,
		    @Param("trailerAllowed") String trailerAllowed,
		    @Param("caravanAllowed") String caravanAllowed
		);

}
