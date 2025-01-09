package com.example.demo.service;

import com.example.demo.domain.Camping;
import com.example.demo.persistence.SearchRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;

    @Autowired
    public SearchServiceImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public List<Camping> searchCampings(
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
        String caravanAllowed
    ) {
        return searchRepository.searchCampings(
            donm,
            sigungunm,
            category,
            campingName,
            flooring,
            startDate,
            endDate,
            bonfire,
            petAllowed,
            trailerAllowed,
            caravanAllowed
        );
    }
}
