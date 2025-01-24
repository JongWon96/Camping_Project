package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Camping;
import com.example.demo.persistence.SearchRepository;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;

    @Autowired
    public SearchServiceImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public Page<Camping> searchCampings(
        String donm,
        String sigungunm,
        String category,
        String campingName,
        String flooring,
        String bonfire,
        String petAllowed,
        String trailerAllowed,
        String caravanAllowed,
        Pageable paging
    )			{    
        
    	return searchRepository.searchCampings(
            donm,
            sigungunm,
            category,
            campingName,
            flooring,
            bonfire,
            petAllowed,
            trailerAllowed,
            caravanAllowed,
            paging
        );
    }
}
