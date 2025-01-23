package com.example.demo.persistence;

import com.example.demo.domain.Review;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCamping_Id(Long campingId);

    List<Review> findByMember_Id(Long memberId);
	@Query("SELECT r FROM Review r WHERE r.danger = 1 ORDER BY r.danger DESC")
	List<Review> getReviewList();

    boolean existsByMemberIdAndCampingId(Long memberId, Long campingId);

	public Page<Review> findReviewByCampingId(Long campingId, Pageable pageable);

	public List<Review> findAllReviewByCampingId(Long campingId);
}
