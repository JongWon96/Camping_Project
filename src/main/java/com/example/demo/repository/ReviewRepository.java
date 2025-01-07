package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCamping_Id(Long campingId); 
    
    List<Review> findByMember_Id(Long memberId); 
}
