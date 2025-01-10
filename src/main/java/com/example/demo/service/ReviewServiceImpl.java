package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Review;
import com.example.demo.persistance.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepo;

	@Override
	public Page<Review> getReview(Long campingId, int page, int size) {
		Pageable paging = PageRequest.of(page - 1, size, Direction.ASC, "reviewdate");

		return reviewRepo.findReviewByCampingId(campingId, paging);
	}

	@Override
	public List<Review> getRate(Long campingId) {
		
		return reviewRepo.findAllReviewByCampingId(campingId);
	}

}
