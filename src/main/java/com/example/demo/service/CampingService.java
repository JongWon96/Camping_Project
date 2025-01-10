package com.example.demo.service;

import org.springframework.data.domain.Page;

import com.example.demo.domain.Camping;

public interface CampingService {

	public Camping getCampingDetail(Long campingId);

	public Page<Camping> getAllCamping(int page, int size);
	
}
