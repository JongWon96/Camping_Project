package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Camping;
import com.example.demo.persistance.CampingRepository;

@Service
public class CampingServiceImpl implements CampingService{

	@Autowired
	private CampingRepository campRepo;
	
	@Override
	public Camping getCampingDetail(Long campingId) {
		
		return campRepo.findById(campingId).get();
	}

	@Override
	public Page<Camping> getAllCamping(int page, int size) {
		
		Pageable paging = PageRequest.of(page - 1, size, Direction.ASC, "facltnm");
		
		return campRepo.findAll(paging);
	}

}
