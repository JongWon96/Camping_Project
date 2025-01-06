package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.CampingService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;

@Controller
public class CampingController {
	
	@Autowired
	private CampingService campingService;
	@Autowired
	private ReviewService reviewService;
	@Autowired 
	private ProductService productService;
	
	
	@GetMapping("/campinglist")
	private String campingList(Model model) {
		
		return "tmp/ListPage";
	}
	
	@GetMapping("/detailpage")
	private String campingDetail() {
		
		return "tmp/DetailPage";
	}
	
	@GetMapping("/cheatsheet")
	private String cheatSheet() {
		
		return "tmp/cheatsheet";
	}
	
	@GetMapping("/landingpage")
	private String landingPage() {
		
		return "tmp/landingPage";
	}

}
