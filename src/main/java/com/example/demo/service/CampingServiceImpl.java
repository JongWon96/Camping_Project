package com.example.demo.service;

import com.example.demo.domain.Camping;
import com.example.demo.persistence.CampingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampingServiceImpl implements CampingService {




    @Autowired CampingRepository campRepo;

    @Override
    public Camping findById(Long id) {
        return campRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Camping not found"));
    }

    @Override
    public Camping getCampingDetail(Long campingId) {

        return campRepo.findById(campingId).get();
    }

    @Override
    public Page<Camping> getAllCamping(int page, int size) {

        Pageable paging = PageRequest.of(page - 1, size, Sort.Direction.ASC, "facltnm");

        return campRepo.findAll(paging);
    }

    @Override
    public List<Camping> getTmpCamping() {

        return campRepo.findAll();
    }

//	@Override
//	public Camping getCampingByProductId(Long productId) {
//
//		return campRepo.findCampingByProductid(productId);
//	}
}
