package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Product;
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
	private String campingList(
			@RequestParam(value = "campingName", required=false) String campingName,
			@RequestParam(value = "searchDo", required=false) String doNm,
			@RequestParam(value = "category", required=false) String category,
			@RequestParam(value = "bonfire", required=false) String bonfire,
			@RequestParam(value = "trailerAllowed", required=false) String trailerAllowed,
			@RequestParam(value = "caravanAllowed", required=false) String caravanAllowed,
			@RequestParam(value = "petAllowed", required=false) String petAllowed,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "9") int size, 
								Model model) {
		
		Page<Camping> CampingPlaces = campingService.getAllCamping(page, size);
		
		model.addAttribute("CampingPlaces", CampingPlaces);
		
		
		/*/
		 * 추천 목록에 띄울 임시 캠핑장 리스트
		 */	
		List<Camping> tmpPlaces = campingService.getTmpCamping();
		
		List<Camping> firstPlace = new ArrayList<>();
		List<Camping> secondPlace = new ArrayList<>();
		List<Camping> thirdPlace = new ArrayList<>();
		
		for (int a = 0 ; a<9 ; a++) {
			if (a<3) {
				firstPlace.add(tmpPlaces.get(a));
			} else if (a<6) {
				secondPlace.add(tmpPlaces.get(a));
			} else {
				thirdPlace.add(tmpPlaces.get(a));
			}
		}
		
		model.addAttribute("firstPlace", firstPlace);
		model.addAttribute("secondPlace", secondPlace);
		model.addAttribute("thirdPlace", thirdPlace);
		

		return "tmp/ListPage";
	}

	@GetMapping("/detailpage")
	private String campingDetail(@RequestParam("campingid") Long campingId,
									@RequestParam(value = "page", defaultValue = "1") int page,
									@RequestParam(value = "size", defaultValue = "10") int size, 
									Model model) {

		Camping campingPlace = campingService.getCampingDetail(campingId);
		List<Product> products = productService.getProducts(campingId);
		
		// 남겨진 평점의 평균으로 평점 출력 -> 데이터가 없어서 에러남
//		List<Review> tmpReviews = reviewService.getRate(campingId);
//		
//		Integer result = 0;
//		
//		for(Review review : tmpReviews) {
//			result += review.getRate();
//		}
//		result = result/(Integer)tmpReviews.size();		
//		result = Math.round(result);
//		
//		model.addAttribute("rate", result);
		
		model.addAttribute("products", products);
		
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
		
		model.addAttribute("CampingPlace", campingPlace);
		model.addAttribute("caravResult", caravResult);
		model.addAttribute("trlerResult", trlerResult);
		
		String[] Sbrscl = campingPlace.getSbrscl().split(",");
		model.addAttribute("Sbrscl", Sbrscl);
		System.out.println(Sbrscl);
		
		Page<Review> reviews = reviewService.getReview(campingId, page, size);

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
