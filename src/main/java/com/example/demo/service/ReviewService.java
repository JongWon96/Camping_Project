package com.example.demo.service;

import com.example.demo.domain.Review;

public interface ReviewService {

    Review saveReview(Long memberId, Long campingId, String content, Integer rate, String img, Integer danger);
}
