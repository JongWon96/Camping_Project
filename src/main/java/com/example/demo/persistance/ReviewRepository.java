package com.example.demo.persistance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	
	public Page<Review> findReviewByCampingId(Long campingId, Pageable pageable);
}
