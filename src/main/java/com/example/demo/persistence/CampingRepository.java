package com.example.demo.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Camping;

import java.util.List;
import com.example.demo.domain.Product;


public interface CampingRepository extends JpaRepository<Camping, Long> {

	Page<Camping> findAll(Pageable Pageable);

	@Query("SELECT c FROM Camping c"
			+ " INNER JOIN Product p ON p.camping.id=c.id"
			+ " WHERE p.id = %?1%")
	Camping findCampingByProductid(Long productId);
	Page<Camping> findByFacltnmContainingIgnoreCase(String facltnm, Pageable pageable);


	// 상품 종류별 조회(상품명으로 검색 지금은. kind였으나, kind가 undefined in productRepo)
	List<Camping> findCampingByFacltnmContaining(String facltnm);

	// 전체상품 조회(상품명으로 검색 포함)
	List<Camping> findCampingsByFacltnmContainingOrderByFacltnm(String facltnm);

	// 전체상품 조회(페이징 처리 포함)
	Page<Camping> findAllCampingsByFacltnmContaining(String facltnm, Pageable pageable);

	Page<Camping> findAllById(Long campingId, Pageable Pageable);
	//
	List<Camping> findCampingByCategoryContaining(String category);


	// camping 테이블에서 다음 id값을 얻는다.
	@Query(value="SELECT MAX(id)+1 FROM camping", nativeQuery = true)
	Long getMaxId();

}
