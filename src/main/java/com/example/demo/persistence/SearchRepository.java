package com.example.demo.persistence;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.Camping;

public interface SearchRepository extends JpaRepository<Camping, Long> {

	@Query(value = """
		    SELECT c
		    FROM Camping c
		    WHERE (:donm IS NULL OR c.donm = :donm)
		      AND (:sigungunm IS NULL OR c.sigungunm = :sigungunm)
		      AND (:category IS NULL OR c.category = :category)
		      AND (:campingName IS NULL OR c.facltnm LIKE CONCAT('%', :campingName, '%'))
		      AND (:flooring IS NULL OR
		           (:flooring = '잔디' AND c.sitebottomcl1 IS NOT NULL AND c.sitebottomcl1 <> '') OR
		           (:flooring = '파쇄석' AND c.sitebottomcl2 IS NOT NULL AND c.sitebottomcl2 <> '') OR
		           (:flooring = '데크' AND c.sitebottomcl3 IS NOT NULL AND c.sitebottomcl3 <> '') OR
		           (:flooring = '자갈' AND c.sitebottomcl4 IS NOT NULL AND c.sitebottomcl4 <> '') OR
		           (:flooring = '맨흙' AND c.sitebottomcl5 IS NOT NULL AND c.sitebottomcl5 <> ''))
		      AND (:bonfire IS NULL OR (:bonfire = 'Y' AND c.eqpmnlendcl LIKE '%화로대%'))
		      AND (:petAllowed IS NULL OR c.animalcmgcl = :petAllowed)
		      AND (:trailerAllowed IS NULL OR c.trleracmpnyat = :trailerAllowed)
		      AND (:caravanAllowed IS NULL OR c.caravacmpnyat = :caravanAllowed)
		    ORDER BY c.facltnm ASC
		""")
		Page<Camping> searchCampings(@Param("donm") String donm, 
		                             @Param("sigungunm") String sigungunm,
		                             @Param("category") String category, 
		                             @Param("campingName") String campingName,
		                             @Param("flooring") String flooring, 
		                             @Param("bonfire") String bonfire,
		                             @Param("petAllowed") String petAllowed, 
		                             @Param("trailerAllowed") String trailerAllowed,
		                             @Param("caravanAllowed") String caravanAllowed,
		                             Pageable pageable);
}