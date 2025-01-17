package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Review;

@Service
public interface ReviewService {

	Review getReview(long id);

	List<Review> getReviewList();
	
	List<Review> getAllReviews();

	void deleteReview(long id);

	void updateReviewResult(int i);
	
	void insertReview(Review Review);

	void updateReview(Review vo);

	//void deleteReview(long id);

	//List<ReservationDetail> getListReservationByFacltnm(String facltnm);

}