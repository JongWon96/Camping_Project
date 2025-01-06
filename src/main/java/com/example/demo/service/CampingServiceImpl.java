package com.example.demo.service;

import com.example.demo.domain.Camping;
import com.example.demo.repository.CampingRepository;
import com.example.demo.service.CampingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampingServiceImpl implements CampingService {

    private final CampingRepository campingRepository;

    @Autowired
    public CampingServiceImpl(CampingRepository campingRepository) {
        this.campingRepository = campingRepository;
    }

    @Override
    public Camping findById(Long id) {
        return campingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Camping not found"));
    }
}
