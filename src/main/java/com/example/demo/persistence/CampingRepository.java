package com.example.demo.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Camping;

public interface CampingRepository extends JpaRepository<Camping, Long> {

	Page<Camping> findAll(Pageable Pageable);

	@Query("SELECT c FROM Camping c" 
			+ " INNER JOIN Product p ON p.camping.id=c.id"
			+ " WHERE p.id = %?1%")
	Camping findCampingByProductid(Long productId);
}
