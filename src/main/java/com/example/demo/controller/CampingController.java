package com.example.demo.controller;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
			@RequestParam(value = "donm", required = false) String donm,
			@RequestParam(value = "sigungunm", required = false) String sigungunm,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "campingName", required = false) String campingName,
			@RequestParam(value = "flooring", required = false) String flooring,
			@RequestParam(value = "startDate", required = false) LocalDate startDate,
			@RequestParam(value = "endDate", required = false) LocalDate endDate,
			@RequestParam(value = "bonfire", required = false) String bonfire,
			@RequestParam(value = "petAllowed", required = false) String petAllowed,
			@RequestParam(value = "trailerAllowed", required = false) String trailerAllowed,
			@RequestParam(value = "caravanAllowed", required = false) String caravanAllowed,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "9") int size, 
			@RequestParam(value = "sort", defaultValue = "facltnm") String sort, Model model) {
		
		// 페이징 정렬 기본 설정 
		// 이름순 : facltnm
		Pageable paging = PageRequest.of(page - 1, size, Direction.ASC, sort);
		
		// 결과 정렬을 위해 전용 변수 추가 : sort
		// 이름순, 낮은 가격순, 높은 가격순, 평점순
		if (sort == "avgRating") {	//평점순
			paging = PageRequest.of(page - 1, size, Direction.DESC, sort);
		} else if (sort == "highPrice") {
			sort = "price";		//합칠때 조절
			paging = PageRequest.of(page - 1, size, Direction.DESC, sort);
		} else if (sort == "lowPrice"){
			sort = "price";		//합칠때 조절
			paging = PageRequest.of(page - 1, size, Direction.ASC, sort);	
		}
			
		
		Page<Camping> CampingPlaces = campingService.getAllCamping(page, size, paging);
		
		//Page<Camping> PagingCampingPlaces = campingService.getSearhResult(doNm, sigungunm, category, campingName, flooring, null, null, bonfire, petAllowed, trailerAllowed, caravanAllowed, page, size);
		
		model.addAttribute("pageInfo", CampingPlaces);
		model.addAttribute("CampingPlaces", CampingPlaces);

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

		// 남겨진 평점의 평균으로 평점 출력 -> 데이터가 없어서 에러남
		Integer result = 0;
		for (Camping place : CampingPlaces) {
			Long campingId = place.getId();
			List<Review> tmpReviews = reviewService.getRate(campingId);
			
			if (tmpReviews.size() != 0) {
				for (Review review : tmpReviews) {
					result += review.getRate();
				}
				result = Math.round(result / (Integer) tmpReviews.size());
			} else {
				result = 0;
			}
		}
		String rate = result.toString();
		model.addAttribute("rate", rate);
		
		// 정렬과 페이징에 필터 요인 추가
		model.addAttribute("donm", donm);
		model.addAttribute("sigungunm", sigungunm);
		model.addAttribute("category", category);
		model.addAttribute("campingName", campingName);
		model.addAttribute("flooring", flooring);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("bonfire", bonfire);
		model.addAttribute("petAllowed", petAllowed);
		model.addAttribute("trailerAllowed", trailerAllowed);
		model.addAttribute("caravanAllowed", caravanAllowed);
		model.addAttribute("sort", sort);

		return "Camping/ListPage";
	}

	@GetMapping("/detailpage")
	private String campingDetail(@RequestParam("campingid") Long campingId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, Model model) {

		Camping campingPlace = campingService.getCampingDetail(campingId);
		List<Product> products = productService.getProducts(campingId);

		// 남겨진 평점의 평균으로 평점 출력
		List<Review> tmpReviews = reviewService.getRate(campingId);
		
		Float result = 0.0f;
		
		if (!tmpReviews.isEmpty()) {
		    double sum = tmpReviews.stream()
		                           .mapToDouble(Review::getRate)
		                           .sum();

		    result = (float) (Math.round((sum / tmpReviews.size()) * 10) / 10.0);
		}

		String rate = result == 0.0f ? "아직 리뷰가 등록되지 않음" : result.toString();

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
		Page<Review> reviews = reviewService.getReview(campingId, page, size);

		model.addAttribute("reviews", reviews);
		
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

	@GetMapping("/landingpage")
	private String landingPage() {

		return "Camping/landingPage";
	}

	//임시 확인용
	@GetMapping("/reviewpage")
	private String reviewPage() {

		return "include/reviewExample";
	}
}
