package com.example.demo.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.Camping;

public interface SearchRepository extends JpaRepository<Camping, Long> {

	@Query(value = """
		    SELECT c.* 
		    FROM Camping c
		    LEFT JOIN Product p ON c.id = p.camping_id
		    LEFT JOIN Reservation r ON r.product_id = p.id
		    WHERE (:donm IS NULL OR c.donm = :donm)
		      AND (:sigungunm IS NULL OR c.sigungunm = :sigungunm)
		      AND (:category IS NULL OR c.category = :category)
		      AND (:campingName IS NULL OR c.facltnm LIKE %:campingName%)
		      AND (:flooring IS NULL OR 
		           (CASE :flooring
		                WHEN '잔디' THEN c.sitebottomcl1
		                WHEN '파쇄석' THEN c.sitebottomcl2
		                WHEN '데크' THEN c.sitebottomcl3
		                WHEN '자갈' THEN c.sitebottomcl4
		                WHEN '맨흙' THEN c.sitebottomcl5
		                ELSE NULL END) <> '0')
		      AND (:bonfire IS NULL OR c.eqpmnlendcl LIKE %:bonfire%)
		      AND (:petAllowed IS NULL OR c.animalcmgcl = :petAllowed)
		      AND (:trailerAllowed IS NULL OR c.trleracmpnyat = :trailerAllowed)
		      AND (:caravanAllowed IS NULL OR c.caravacmpnyat = :caravanAllowed)
		      AND (:startDate IS NULL OR :endDate IS NULL OR
		           NOT EXISTS (
		               SELECT 1 FROM Reservation r
		               WHERE r.product_id = p.id
		                 AND ((r.checkin BETWEEN :startDate AND :endDate) OR
		                      (r.checkout BETWEEN :startDate AND :endDate))
		           ))
		      AND (p.roomcount -
		          COALESCE(
		              (SELECT COUNT(*) FROM Reservation r
		               WHERE r.product_id = p.id
		                 AND ((r.checkin BETWEEN :startDate AND :endDate) OR
		                      (r.checkout BETWEEN :startDate AND :endDate))), 0) > 0)
		    """, nativeQuery = true)
		List<Camping> searchCampings(
		    @Param("donm") String donm,
		    @Param("sigungunm") String sigungunm,
		    @Param("category") String category,
		    @Param("campingName") String campingName,
		    @Param("flooring") String flooring,
		    @Param("startDate") LocalDate startDate,
		    @Param("endDate") LocalDate endDate,
		    @Param("bonfire") String bonfire,
		    @Param("petAllowed") String petAllowed,
		    @Param("trailerAllowed") String trailerAllowed,
		    @Param("caravanAllowed") String caravanAllowed
		);
}
