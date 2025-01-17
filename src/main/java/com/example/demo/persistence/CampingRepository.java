package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Product;
//import com.example.demo.domain.ProductComment;
//import com.example.demo.domain.Camping;

public interface CampingRepository extends JpaRepository<Camping, Long> {

	Page<Camping> findByFacltnmContainingIgnoreCase(String facltnm, Pageable pageable);
	
	
	// 상품 종류별 조회(상품명으로 검색 지금은. kind였으나, kind가 undefined in productRepo)
	List<Camping> findCampingByFacltnmContaining(String facltnm);
	
	// 전체상품 조회(상품명으로 검색 포함)
	List<Camping> findCampingsByFacltnmContainingOrderByFacltnm(String facltnm);
	
	// 전체상품 조회(페이징 처리 포함)
	Page<Camping> findAllCampingsByFacltnmContaining(String facltnm, Pageable pageable);

	// 
	List<Camping> findCampingByCategoryContaining(String category);
	
	
	Page<Camping> findAll(Pageable pageable);
	
	// camping 테이블에서 다음 id값을 얻는다.
	@Query(value="SELECT MAX(id)+1 FROM camping", nativeQuery = true)
	Long getMaxId();
	
}
