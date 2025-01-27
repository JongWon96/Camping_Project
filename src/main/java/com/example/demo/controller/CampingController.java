package com.example.demo.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Member;
import com.example.demo.domain.Product;
import com.example.demo.domain.Review;
import com.example.demo.service.CampingService;
import com.example.demo.service.LikesService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.SearchService;

import jakarta.servlet.http.HttpSession;

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

	@Autowired
	private RecommendationController recommendationController;

	@Autowired
	private LikesService likesService;

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
	        @RequestParam(value = "sort", defaultValue = "facltnm") String sort,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "9") int size,
	        Model model, HttpSession session
	) {		
		// 결과 정렬을 위해 전용 변수 추가 : sort
		// 이름순, 낮은 가격순, 높은 가격순, 평점순
		System.out.println("sort:" + sort);
		
		Pageable paging = PageRequest.of(page - 1, size, Direction.ASC, "facltnm");
		
		if ("avgRating".equals(sort)) {  
		    paging = PageRequest.of(page - 1, size, Direction.DESC, "avgRating");
		} else if ("highprice".equals(sort)) {
		    paging = PageRequest.of(page - 1, size, Direction.DESC, "price");
		} else if ("lowprice".equals(sort)) {
		    paging = PageRequest.of(page - 1, size, Direction.ASC, "price");
		} else if ("facltnm".equals(sort)) {
		    paging = PageRequest.of(page - 1, size, Direction.ASC, "facltnm");
		}
		
		System.out.println("pagnig:" + paging.getSort());
		
	    Page<Camping> campingPlaces = searchService.searchCampings(
	            donm, sigungunm, category, campingName, flooring,
	             bonfire, petAllowed, trailerAllowed, caravanAllowed, paging
	    );	
		model.addAttribute("pageInfo", campingPlaces);
		model.addAttribute("CampingPlaces", campingPlaces.getContent());

		Member loginUser = (Member) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		//임시로 출력했던 부분(파이썬 프로그램이 없음)
//		List<Camping> tmpPlaces = campingService.getTmpCamping();
//		
//		List<Camping> firstPlace = new ArrayList<>();
//		List<Camping> secondPlace = new ArrayList<>();
//		List<Camping> thirdPlace = new ArrayList<>();
//
//		for (int a = 0; a < 9; a++) {
//			if (a < 3) {
//				firstPlace.add(tmpPlaces.get(a));
//			} else if (a < 6) {
//				secondPlace.add(tmpPlaces.get(a));
//			} else {
//				thirdPlace.add(tmpPlaces.get(a));
//			}
//		}
//
//		model.addAttribute("firstPlace", firstPlace);
//		model.addAttribute("secondPlace", secondPlace);
//		model.addAttribute("thirdPlace", thirdPlace);

		// 페이징에 필터 요인 추가
		model.addAttribute("donm", donm);
		model.addAttribute("sigungunm", sigungunm);
		model.addAttribute("category", category);
		model.addAttribute("campingName", campingName);
		model.addAttribute("flooring", flooring);
		model.addAttribute("bonfire", bonfire);
		model.addAttribute("petAllowed", petAllowed);
		model.addAttribute("trailerAllowed", trailerAllowed);
		model.addAttribute("caravanAllowed", caravanAllowed);
		model.addAttribute("sort", sort);

		// 파이썬 프로그렘이 없어서 출력안됨
		String campingPage = recommendationController.getCampingRecommendations(
				session,
				9, // 기본 추천 개수
				0.5, // 리뷰 가중치
				0.3, // 예약 가중치
				0.2, // 위시리스트 가중치
				model
		);

		return "Camping/ListPage";
	}

	@GetMapping("/detailpage")
	private String campingDetail(@RequestParam("campingid") Long campingId,
								 @RequestParam(value = "page", defaultValue = "1") int page,
								 @RequestParam(value = "size", defaultValue = "10") int size, Model model,
								 HttpSession session) {

		// 로그인 사용자 정보
		Member loginUser = (Member) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);

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
	     else {
			result = 0f;
		}
		String rate = result.toString();

		if ("0".equals(rate)) {
			rate = "아직 리뷰가 등록되지 않음";
		}


		 rate = result == 0.0f ? "아직 리뷰가 등록되지 않음" : result.toString();

		model.addAttribute("rate", rate);

        //
		String carav = campingPlace.getCaravacmpnyat();
		String trler = campingPlace.getTrleracmpnyat();

		String caravResult = "";
		String trlerResult = "";
		if ("Y".equals(carav)) {
			caravResult = "가능";
		} else {
			caravResult = "불가능";
		}

		if ("Y".equals(trler)) {
			trlerResult = "가능";
		} else {
			trlerResult = "불가능";
		}

		model.addAttribute("CampingPlace", campingPlace);
		model.addAttribute("caravResult", caravResult);
		model.addAttribute("trlerResult", trlerResult);
		
		//String[] Sbrscl = campingPlace.getSbrscl().split(",");
		String[] splitValues = new String[15];
		
		String sbrscl = campingPlace.getSbrscl();
		if (sbrscl != null) {
			splitValues = sbrscl.split(",");
		} else {
		    splitValues = null;
		}
		model.addAttribute("Sbrscl", splitValues);
		
		// 리뷰 전달
		Page<Review> reviews = reviewService.getReview(campingId, page, size);
		model.addAttribute("reviews", reviews);

		// 방 출력
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);

		Product Room1 = products.get(0);
		model.addAttribute("Room1", Room1);

		int price1 = (int) Room1.getPrice().doubleValue();
		String formattedPrice1 = numberFormat.format(price1);
		model.addAttribute("price1", formattedPrice1);

		Product Room2 = products.get(1);
		model.addAttribute("Room2", Room2);

		int price2 = (int) Room2.getPrice().doubleValue();
		String formattedPrice2 = numberFormat.format(price2);
		model.addAttribute("price2", formattedPrice2);

		Product Room3 = products.get(2);
		model.addAttribute("Room3", Room3);

		int price3 = (int) Room3.getPrice().doubleValue();
		String formattedPrice3 = numberFormat.format(price3);
		model.addAttribute("price3", formattedPrice3);

		// **찜 상태 확인 추가**
		if (loginUser != null) {
			Long memberId = loginUser.getId();
			boolean isLiked = likesService.isLiked(memberId, campingId);
			model.addAttribute("isLiked", isLiked); // 찜 상태 추가
		} else {
			model.addAttribute("isLiked", false); // 비로그인 시 기본값 false
		}




		model.addAttribute("math", Math.class);

		return "Camping/DetailPage";
	}

	@PostMapping("/toggle-like")
	public ResponseEntity<String> toggleLike(@RequestParam("campingid") Long campingId, HttpSession session) {
		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		Long memberId = loginUser.getId();

		try {
			System.out.println("Processing toggle-like for Member ID: " + memberId + ", Camping ID: " + campingId);

			// 찜 상태 확인
			boolean alreadyLiked = likesService.isLiked(memberId, campingId);
			if (alreadyLiked) {
				// 찜 삭제
				System.out.println("Removing like...");
				likesService.removeLike(memberId, campingId);
				return ResponseEntity.ok("찜이 삭제되었습니다.");
			} else {
				// 찜 추가
				System.out.println("Adding like...");
				likesService.saveLike(memberId, campingId);
				return ResponseEntity.ok("찜이 추가되었습니다.");
			}
		} catch (IllegalArgumentException e) {
			System.err.println("IllegalArgumentException: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("찜 상태 변경 중 오류가 발생했습니다.");
		}
	}
	
	@GetMapping("/landingpage")
	private String landingPage(HttpSession session , Model model) {
		
		Member loginUser = (Member) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		
		//임시 출력용
		//임시로 출력했던 부분(파이썬 프로그램이 없음)
//		List<Camping> tmpPlaces = campingService.getTmpCamping();
//		
//		List<Camping> firstPlace = new ArrayList<>();
//		List<Camping> secondPlace = new ArrayList<>();
//		List<Camping> thirdPlace = new ArrayList<>();
//
//		for (int a = 0; a < 9; a++) {
//			if (a < 3) {
//				firstPlace.add(tmpPlaces.get(a));
//			} else if (a < 6) {
//				secondPlace.add(tmpPlaces.get(a));
//			} else {
//				thirdPlace.add(tmpPlaces.get(a));
//			}
//		}
//
//		model.addAttribute("firstPlace", firstPlace);
//		model.addAttribute("secondPlace", secondPlace);
//		model.addAttribute("thirdPlace", thirdPlace);
		
		//렌딩 페이지에 추천 결과 추가(추천 프로그램 없음)
		String campingPage = recommendationController.getCampingRecommendations(
				session,
				9, // 기본 추천 개수
				0.5, // 리뷰 가중치
				0.3, // 예약 가중치
				0.2, // 위시리스트 가중치
				model
		);

		return "Camping/landingPage";
	}

	//임시 확인용
	@GetMapping("/reviewpage")
	private String reviewPage(HttpSession session , Model model) {
		Member loginUser = (Member) session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);
		return "include/reviewExample";
	}
}
