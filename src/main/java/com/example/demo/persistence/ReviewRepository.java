package com.example.demo.persistence;

import com.example.demo.domain.Review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCamping_Id(Long campingId);

    List<Review> findByMember_Id(Long memberId);

    boolean existsByMemberIdAndCampingId(Long memberId, Long campingId);

public interface ReviewRepository extends JpaRepository<Review, Long>{


	public Page<Review> findReviewByCampingId(Long campingId, Pageable pageable);

	public List<Review> findAllReviewByCampingId(Long campingId);
}
