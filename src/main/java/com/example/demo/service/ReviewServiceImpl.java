package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Review;
import com.example.demo.persistence.ReviewRepository;


@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepo;
	
	@Override
	public void insertReview(Review Review) {
		
		reviewRepo.save(Review);
	}
	
	@Override
	public Review getReview(long id) {
		
		return reviewRepo.findById(id).get();
	}
	

	@Override
	public List<Review> getAllReviews() {
		List<Review> reviews = reviewRepo.findAll();
	     if (reviews.isEmpty()) {
	            // 리뷰가 없다면 빈 리스트 반환
	            return new ArrayList<>(); 
	        }
		return reviews;
	}

	@Override
	public void updateReview(Review vo) {
		Optional<Review> result = reviewRepo.findById(vo.getId());
		
		if (result.isPresent()) {
			Review review = result.get();
			
			//reservation.setReply(vo.getReply());
			
			reviewRepo.save(review);
		}
		
	}
	
	@Override
	public void deleteReview(long id) {
		 
	   reviewRepo.deleteById(id); // 삭제 작업
		     
	}
	@Override
	public List<Review> getReviewList() {
	
		return reviewRepo.getReviewList();
	}
/*
	@Override
	public List<ReservationDetail> getListReviewByFacltnm(String facltnm) {
		// TODO Auto-generated method stub
		return null;
	}
*/
	@Override
	public void updateReviewResult(int i) {
		// TODO Auto-generated method stub
		
	}
}









