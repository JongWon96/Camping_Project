package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.domain.Review;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {

	

    // 후기 저장 메소드 (이미지 파일을 받기 위한 MultipartFile 추가)
    public Review saveReview(Long memberId, Long campingId, String content, Integer rate, MultipartFile imgFile ,Integer danger) throws IOException;

    // 후기가 작성되었는지 확인하는 메소드 추가
    boolean hasReview(Long memberId, Long campingId);

    public List<Review> getReviewsByCampingId(Long campingId);

    public void deleteReviewImage(Review review);

    public void updateReview(Long reviewId, String content, Integer rate, MultipartFile imgFile,Integer danger)throws IOException;

    public void deleteReview(Long reviewId)throws IOException ;

    public Review getReviewById(Long reviewId);

    public void dangerReview(Review review);


}