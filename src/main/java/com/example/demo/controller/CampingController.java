package com.example.demo.controller;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.example.demo.service.SearchService;

@Controller
public class CampingController {

	@Autowired
	private CampingService campingService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SearchService searchService;

    public CampingController(CampingService campingService) {
        this.campingService = campingService;
    }
	
	@GetMapping("/landingpage")
	private String landingPage() {

		return "Camping/landingPage";
	}
	
	@GetMapping("/campinglist")
	private String campingList(
	        @RequestParam(value = "campingName", required = false) String campingName,
	        @RequestParam(value = "donm", required = false) String donm,
	        @RequestParam(value = "sigungunm", required = false) String sigungunm,
	        @RequestParam(value = "category", required = false) String category,
	        @RequestParam(value = "flooring", required = false) String flooring,
	        @RequestParam(value = "bonfire", required = false) String bonfire,
	        @RequestParam(value = "petAllowed", required = false) String petAllowed,
	        @RequestParam(value = "trailerAllowed", required = false) String trailerAllowed,
	        @RequestParam(value = "caravanAllowed", required = false) String caravanAllowed,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "9") int size,
	        Model model
	) {
	    // 검색 조건에 따른 캠핑장 데이터 조회
	    Page<Camping> campingPlaces = searchService.searchCampings(
	            donm, sigungunm, category, campingName, flooring,
	             bonfire, petAllowed, trailerAllowed, caravanAllowed, page, size
	    );
		
		model.addAttribute("pageInfo", campingPlaces);
		model.addAttribute("CampingPlaces", campingPlaces);

		/*
		 * / 추천 목록에 띄울 임시 캠핑장 리스트
		 */
		List<Camping> tmpPlaces = campingService.getTmpCamping();

		List<Camping> firstPlace = new ArrayList<>();
		List<Camping> secondPlace = new ArrayList<>();
		List<Camping> thirdPlace = new ArrayList<>();

		for (int a = 0; a < 9; a++) {
			if (a < 3) {
				firstPlace.add(tmpPlaces.get(a));
			} else if (a < 6) {
				secondPlace.add(tmpPlaces.get(a));
			} else {
				thirdPlace.add(tmpPlaces.get(a));
			}
		}

		model.addAttribute("firstPlace", firstPlace);
		model.addAttribute("secondPlace", secondPlace);
		model.addAttribute("thirdPlace", thirdPlace);
		
		System.out.println(campingPlaces.getContent());
		
		return "Camping/ListPage";
	}

	@GetMapping("/detailpage")
	private String campingDetail(@RequestParam("campingid") Long campingId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, Model model) {

		Camping campingPlace = campingService.getCampingDetail(campingId);
		List<Product> products = productService.getProducts(campingId);

		// 남겨진 평점의 평균으로 평점 출력
		List<Review> tmpReviews = reviewService.getReviewsByCampingId(campingId);
		
		Integer result = 0;
		
		if (!tmpReviews.isEmpty()) {
			double sum = 0.0;
			for(Review review : tmpReviews) {
				 sum += review.getRate();
			}
			double average = sum / tmpReviews.size();		
			result = (int) Math.round(average);
		} else {
			result = 0;
		} 
		String rate = result.toString();

		if ("0".equals(rate)) {
			rate = "아직 리뷰가 등록되지 않음";
		} 

		model.addAttribute("rate", rate);

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
		
		//리뷰 전달
		//Page<Review> reviews = reviewService.getReview(campingId, page, size);

		//model.addAttribute("reviews", reviews);
		
		//방 출력
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
		
		Product Room1 = products.get(0);
		model.addAttribute("Room1", Room1);
		
		int price1 = (int)Room1.getPrice().doubleValue();
		String formattedPrice1 = numberFormat.format(price1);
		model.addAttribute("price1", formattedPrice1);
		
		Product Room2 = products.get(1);
		model.addAttribute("Room2", Room2);
		
		int price2 = (int)Room2.getPrice().doubleValue();
		String formattedPrice2 = numberFormat.format(price2);
		model.addAttribute("price2", formattedPrice2);
		
		Product Room3 = products.get(2);
		model.addAttribute("Room3", Room3);
		
		int price3 = (int)Room3.getPrice().doubleValue();		
		String formattedPrice3 = numberFormat.format(price3);
		model.addAttribute("price3", formattedPrice3);
		
		model.addAttribute("math", Math.class);
		
		return "Camping/DetailPage";
	}

    @GetMapping("/camping/list")
    public String listCampings(@RequestParam(required = false, defaultValue = "rating") String sort, Model model) {
        List<Camping> campings;

        switch (sort) {
            case "priceLow":
                campings = campingService.getCampingByLowestPriceAsc();
                break;
            case "priceHigh":
                campings = campingService.getCampingByHighestPriceDesc();
                break;
            default:
                campings = campingService.getCampingByAvgRatingDesc();
        }

        model.addAttribute("campings", campings);
        return "camping/listPage";
    }
}
