package com.example.demo.persistence;

import com.example.demo.domain.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCamping_Id(Long campingId); 
    
    List<Review> findByMember_Id(Long memberId); 
    
	boolean existsByMemberIdAndCampingId(Long memberId, Long campingId);
    
}
