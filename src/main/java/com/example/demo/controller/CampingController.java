package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Review;
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
	private String campingList(@RequestParam(value = "page", defaultValue = "1") int page,
								@RequestParam(value = "size", defaultValue = "10") int size, 
								Model model) {
		
		Page<Camping> campingPlaces = campingService.getAllCamping(null, 0, 0);
		
		model.addAttribute("campingPlaces", campingPlaces);

		return "tmp/ListPage";
	}

	@GetMapping("/detailpage")
	private String campingDetail(@RequestParam("campingid") Long campingId,
									@RequestParam(value = "page", defaultValue = "1") int page,
									@RequestParam(value = "size", defaultValue = "10") int size, 
									Model model) {

		Camping campingPlace = campingService.getCampingDetail(campingId);
		
		String carav = campingPlace.getCaravacmpnyat();
		String trler = campingPlace.getTrleracmpnyat();
		
		String caravResult = "";
		String trlerResult = "";
		if (carav == "Y") {
			caravResult = "가능";
		} else {
			caravResult = "불가능";
		}
		
		if (trler == "Y") {
			trlerResult = "가능";
		} else {
			trlerResult = "불가능";
		}
		
		model.addAttribute("campingPlace", campingPlace);
		model.addAttribute("caravResult", caravResult);
		model.addAttribute("trlerResult", trlerResult);
		
		
		Page<Review> reviews = reviewService.getReview(campingId, 0, 0);

		model.addAttribute("reviews", reviews);

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
