package com.example.demo.service;

import org.springframework.data.domain.Page;

import com.example.demo.domain.Review;

public interface ReviewService {

	public Page<Review> getReview(Long campingId, int page, int size);
	
	
}
