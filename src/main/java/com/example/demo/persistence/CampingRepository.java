package com.example.demo.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Camping;

public interface CampingRepository extends JpaRepository<Camping, Long> {

	Page<Camping> findAllById(Long campingId, Pageable Pageable);

	
}
