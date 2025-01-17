package com.example.demo.persistence;

import com.example.demo.domain.Camping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampingRepository extends JpaRepository<Camping, Long> {

	Page<Camping> findAllById(Long campingId, Pageable Pageable);
	
}