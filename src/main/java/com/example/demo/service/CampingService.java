package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.domain.Camping;

public interface CampingService {

	public Camping getCampingDetail(Long campingId);

	public Page<Camping> getAllCamping(int page, int size);
	
	public List<Camping> getTmpCamping();
	
	public Camping getCampingByProductId(Long productId);

	public Camping findById(Long campingId);
	
	public Page<Camping> getSearhResult(
			String donm, 
			String sigungunm, 
			String category, 
			String campingName,
			String flooring, 
			LocalDate startDate, 
			LocalDate endDate, 
			String bonfire, 
			String petAllowed, 
			String trailerAllowed, 
			String carananAllowed,
			int page, int size);
	
}
