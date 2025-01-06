package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCampingId(Long campingId); 

    List<Review> findByMemberId(Long memberId); 
}
