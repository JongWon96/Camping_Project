package com.example.demo.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query("SELECT p FROM Product p "
				+ " WHERE p.camping.id = %?1%"
				+ " ORDER BY p.room ASC")
	List<Product> findByCampingId(Long campingId);
	
	@Query("SELECT p FROM Product p "
			+ " WHERE p.id = %?1%"
			+ " AND p.room = %?2%")
	Product findByRoom(Long productId, Integer roomnum);

	
}
