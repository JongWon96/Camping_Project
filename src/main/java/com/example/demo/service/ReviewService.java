package com.example.demo.service;

import com.example.demo.domain.Review;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Review;

@Service
public interface ReviewService {

    // 후기 저장 메소드 (이미지 파일을 받기 위한 MultipartFile 추가)
    public Review saveReview(Long memberId, Long campingId, String content, Integer rate, MultipartFile imgFile ,Integer danger) throws IOException;
	Review getReview(long id);

    // 후기가 작성되었는지 확인하는 메소드 추가
    boolean hasReview(Long memberId, Long campingId);

    public List<Review> getReviewsByCampingId(Long campingId);
	List<Review> getReviewList();

	List<Review> getAllReviews();

    public void deleteReviewImage(Review review);
	void deleteReview(long id);

    public void updateReview(Long reviewId, String content, Integer rate, MultipartFile imgFile,Integer danger)throws IOException;

    public void deleteReview(Long reviewId)throws IOException ;
	void updateReviewResult(int i);

	void insertReview(Review Review);

    public Review getReviewById(Long reviewId);
	void updateReview(Review vo);

    public void dangerReview(Review review);
	//void deleteReview(long id);

    public Page<Review> getReview(Long campingId, int page, int size);
	//List<ReservationDetail> getListReservationByFacltnm(String facltnm);


    public List<Review> getRate(Long campingId);
}