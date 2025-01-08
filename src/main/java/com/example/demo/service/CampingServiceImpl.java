package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.persistence.CampingRepository;

@Service
public class CampingServiceImpl implements CampingService {

    private final CampingRepository campingRepository;

    @Autowired
    public CampingServiceImpl(CampingRepository campingRepository) {
        this.campingRepository = campingRepository;
    }

    @Override
    public List<String> getAllDonm() {
        return campingRepository.findDistinctDonm();
    }

    @Override
    public List<String> getSigungunmByDonm(String donm) {
        return campingRepository.findSigungunmByDonm(donm);
    }
	
}
