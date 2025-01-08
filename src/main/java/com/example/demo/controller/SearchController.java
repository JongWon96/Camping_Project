package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CampingService;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final CampingService campingService;

    @Autowired
    public SearchController(CampingService campingService) {
        this.campingService = campingService;
    }

    @GetMapping("/donm")
    public List<String> getAllDonm() {
        return campingService.getAllDonm();
    }

    @GetMapping("/sigungunm/{donm}")
    public List<String> getSigungunmByDonm(@PathVariable String donm) {
        return campingService.getSigungunmByDonm(donm);
    }
}
