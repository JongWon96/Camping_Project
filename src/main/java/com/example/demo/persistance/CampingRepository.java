package com.example.demo.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Camping;

public interface CampingRepository extends JpaRepository<Camping, Long> {

	
	
}
