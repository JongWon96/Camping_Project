package com.example.demo.persistance;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	
	public Page<Review> findReviewByCampingId(Long campingId, Pageable pageable);
	
	public List<Review> findAllReviewByCampingId(Long campingId);
}
