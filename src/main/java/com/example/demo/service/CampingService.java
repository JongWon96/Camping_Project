package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Product;

public interface CampingService {

	public Page<Camping> getAllCampingByFacltnm(String facltnm, int page, int size);

	public Camping getCamping(long id);

	public List<Camping> getCampingListByCategory(String category);

	public List<Camping> getAllCampings(String facltnm);

	public void insertCamping(Camping vo);

	public void updateCamping(Camping vo);

	public Page<Camping> getAllCampingsByFacltnm(String facltnm, int page, int size);

	public Page<Camping> getAllCamping(Pageable pageable);
	
	public Camping getCampingDetail(Long campingId);

	public Page<Camping> getAllCamping(int page, int size);

	public List<Camping> getTmpCamping();

	public Camping getCampingByProductId(Long productId);
	public Camping findById(Long campingId);
	
	/*
	public Page<Camping> getSearhResult(
			String donm, 
			String sigungunm, 
			String category, 
=======*/


//	public Page<Camping> getSearhResult(
//			String donm,
//			String sigungunm,
//			String category,
//			String campingName,
//			String flooring,
//			String bonfire,
//			String petAllowed,
//			String trailerAllowed,
//			String carananAllowed,
//			int page, int size);
	
    List<Camping> getCampingByAvgRatingDesc();
    List<Camping> getCampingByLowestPriceAsc();
    List<Camping> getCampingByHighestPriceDesc();
	
}
