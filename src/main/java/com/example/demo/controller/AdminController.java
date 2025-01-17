package com.example.demo.controller;

import org.springframework.data.domain.Pageable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Admin;
import com.example.demo.domain.Camping;
import com.example.demo.domain.Inquiry;
import com.example.demo.domain.Member;
import com.example.demo.domain.Product;
import com.example.demo.domain.Reservation;
import com.example.demo.domain.ReservationDetail;
import com.example.demo.domain.Review;
//import com.example.demo.domain.Inquiry;
//import com.example.demo.dto.SalesCountInterface;
import com.example.demo.service.AdminService;
import com.example.demo.service.CampingService;
import com.example.demo.service.InquiryService;
import com.example.demo.service.MemberService;
import com.example.demo.service.ReservationService;
import com.example.demo.service.ReviewService;

@Controller
@SessionAttributes("adminUser")
public class AdminController {

	@Autowired
	private AdminService adminService;	
	@Autowired
	private MemberService memberService;
	
	/*@Autowired
	private reservationService reservationService;
	*/
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private CampingService campingService;
	@Autowired
	private InquiryService inquiryService;	

	@Autowired
	private ReviewService reviewService;	
	
	
	// 파일 업로드 경로 변수 선언(application.properties 파일에서 속성값 읽기)
	@Value("${com.demo.upload.path}")
	private String uploadPath;
	
	@GetMapping("/admin_login_form")
	public String adminLoginView() {
		
		return "admin/main";
	}
	
	// 관리자 로그인 구현
	@PostMapping("/admin_login")
	public String adminLogin(Admin vo, Model model) {
		  String url = "";
		    
		    // id 값이 잘못된 경우 처리
		    try {
		        Long id = Long.valueOf(vo.getId().toString());  // id 값이 Long 타입으로 변환되는지 확인
		        vo.setId(id);
		    } catch (NumberFormatException e) {
		        model.addAttribute("message", "아이디는 숫자만 입력 가능합니다.");
		        return "admin/main";  // 아이디 입력 오류 처리
		    }
		    
		    // (1) 관리자 계정 인증 호출: adminCheck()
		    long result = adminService.adminCheck(vo);
		    
		    // (2) 인증 결과에 따라 
		    if (result == 1) {  // 정상 사용자
		        Admin admin = adminService.getAdmin(vo.getId());
		        model.addAttribute("adminUser", admin);
		        url = "redirect:admin_camping_list";
		    } else {
		        model.addAttribute("message", "아이디 또는 비밀번호가 맞지 않습니다.");
		        url = "admin/main";
		    }
		    
		    return url;
	}
	
	@GetMapping("/admin_logout")
	public String adminLogout(SessionStatus status) {
		
		status.setComplete();  // 세션 종료
		
		return "admin/main";
	}
	
	
	/*
	 * 페이징 기능을 추가한 전체 상품 목록 조회
	 */
	
	@GetMapping("/admin_camping_list")
	public String showCampingList(
	        @RequestParam(value = "key", defaultValue = "") String facltnm,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        Model model) {
		
		 Pageable pageable = PageRequest.of(page - 1, size);
		 
	    Page<Camping> pageInfo = campingService.getAllCampingByFacltnm(facltnm, page, size);
	    
	    model.addAttribute("campingList", pageInfo.getContent());
	    model.addAttribute("pageInfo", pageInfo);

	    return "admin/camping/campingList";
	}
	
	@PostMapping("/admin_camping_list")
	public String handlePostRequest() {
	    // 처리 로직
	    return "redirect:/admin_camping_list";  // 처리 후 리디렉션
	}

	@GetMapping("/admin_camping_write_form")
	public String adminCampingWriteView(Model model) {
		String[] categoryList = {"일반캠핑", "오토캠핑", "글램핑", "카라반"};
		
		model.addAttribute("categoryList", categoryList);
		
		return "admin/camping/campingWrite";
	}
	
	/* 
	 * 상품 등록 처리
	 */
	@PostMapping("/admin_camping_write")
	public String adminCampingWriteAction(Camping vo, 
					@RequestParam(value="camping_image") MultipartFile uploadFile) {
		
		if (!uploadFile.isEmpty()) { // 입력 이미지파일이 있으면
			String fileName = uploadFile.getOriginalFilename(); // 파일명 추출
			
			// 파일명이 중복되지 않도록 rename
			// UUID - 세계적으로 유니크한 ID 생성
			String uuid = UUID.randomUUID().toString();
			
			// 새로운 파일명 생성
			String saveName = uuid + "_" + fileName;
			vo.setFirstimageurl(saveName);
			
			// 서버로 파일 업로드
			try {
				uploadFile.transferTo(new File(uploadPath + File.separator + saveName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		
		campingService.insertCamping(vo);
		
		return "redirect:admin_camping_list";
	}
	
	 @PostMapping("/updateCamping")
	    public String updateCamping(
	        @RequestParam("id") String id,
	        @RequestParam("category") String category,
	        @RequestParam("facltnm") String facltnm,
	        @RequestParam("addr1") String addr1,
	        @RequestParam("roomcount") int roomcount,
	        @RequestParam("firstimageurl") MultipartFile firstimageurl) {
	        
	        // 파일 처리
	        if (!firstimageurl.isEmpty()) {
	            // 파일 저장 로직 처리
	            String fileName = firstimageurl.getOriginalFilename();
	        //  firstimageurl.transferTo(new File("E:\\Student\\uploads\\" + fileName));
	        }
	        
	        // 다른 파라미터 처리
	        
	        return "success";
	    }
	
	/*
	 * 상품 상세정보 조회 처리
	 */
	@GetMapping("/admin_camping_detail")
	public String adminCampingDetailAction(Camping vo, Model model) {
		String[] categoryList = {"", "일반캠핑", "오토캠핑", "글램핑", "카라반"};
		Camping camping = campingService.getCamping(vo.getId());
		
		model.addAttribute("campingVO", camping);
		model.addAttribute("category", camping.getCategory());
		
		return "admin/camping/campingDetail";
	}
	
    
	
	@PostMapping("/admin_camping_update_form")
	public String adminCampingUpdateView(Camping vo, Model model) {
		String[] categoryList = {"일반캠핑", "오토캠핑", "글램핑", "카라반"};
		// 수정할 켐핑 조회
		Camping camping = campingService.getCamping(vo.getId());
		
		model.addAttribute("campingVO", camping);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("category", camping.getCategory());
		
		return "admin/camping/campingUpdate";
	}
	
	@PostMapping("/admin_camping_update")
	public String adminCampingUpdate(Camping vo,
			@RequestParam(value="camping_image") MultipartFile uploadFile,
			@RequestParam(value="nonmakeImg") String org_image) {

		if (!uploadFile.isEmpty()) { // 입력 이미지파일이 있으면
			String fileName = uploadFile.getOriginalFilename(); // 파일명 추출
			
			// 파일명이 중복되지 않도록 rename
			// UUID - 세계적으로 유니크한 ID 생성
			String uuid = UUID.randomUUID().toString();
			
			// 새로운 파일명 생성
			String saveName = uuid + "_" + fileName;
			vo.setFirstimageurl(saveName);
			
			// 서버로 파일 업로드
			try {
				uploadFile.transferTo(new File(uploadPath + File.separator + saveName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		} else {  // 이미지 파일이 수정되지 않았으면
			vo.setFirstimageurl(org_image);
		}

		campingService.updateCamping(vo);
		System.out.println("update camping" + vo);
		
		return "redirect:admin_camping_list";
	}
	
	/*
	 * 회원 전체목록 조회 처리
	 */
	@RequestMapping("/admin_member_list")
	public String adminMemberList(
			@RequestParam(value="key", defaultValue="") String name,
			Model model) {
		List<Member> memberList = memberService.getMemberList(name);
		model.addAttribute("memberList", memberList);
		
		return "admin/member/memberList";
	}
	
	/*
	 * 주문 전체내역 조회 처리
	 */
	
	@RequestMapping("/admin_reservation_list")
	public String adminReservationList(
			Model model) {
		
	    
		List<Reservation> reservationList = reservationService.getReservationList();
		
		model.addAttribute("reservationList", reservationList);
		
		return "admin/reservation/reservationList";
	}
	
	
	/*
	 * 주문처리 결과 수정
	 */
	
	@PostMapping("/admin_reservation_save")
	public String adminReservationSave(
			//@RequestParam(value="selectedIds") String selectedIds,
			@RequestParam(value="result") long[] reserve_ids) {
		
		//String[] idArray = selectedIds.split(",");
	    
	    // 각 예약을 처리 완료로 변경
	    for (long id : reserve_ids) {
	    	System.out.println("예약 처리 ID: " + id);
	        Reservation reservation = reservationService.getReservationById(id);
	        if (reservation != null) {
	            reservationService.updateReservationResult(id, "1");  // 예약 상태 업데이트
	        }
	    }
		
		return "redirect:admin_reservation_list";
	}
	
	@RequestMapping("/admin_inquiry_list")
	public String adminInquiryList(Model model) {
		// Inquiry 전체 목록 조회
		List<Inquiry> inquiryList = inquiryService.getAllInquiry();
		
		model.addAttribute("inquiryList", inquiryList);
		
		return "admin/inquiry/inquiryList";
	}
	
	@PostMapping("/admin_inquiry_detail")
	public String adminInquiryDetail(Inquiry vo, Model model) {
		// (1) Inquiry 일련번호를 조건으로 Inquiry 조회
		Inquiry inquiry = inquiryService.getInquiry(vo.getId());
		
		// (2) InquiryVO 속성에 데이터 저장
		model.addAttribute("inquiryVO", inquiry);
		
		// (3) InquiryDetail 화면 호출
		return "admin/inquiry/inquiryDetail";
	}
	
	@PostMapping("/admin_inquiry_replysave")
	public String adminInquiryReplySave(//@RequestParam("id") Long id,
            //@RequestParam("reply") String reply,
            //@RequestParam("status") Integer status,
            Inquiry vo) {
		
		 // 1. inquiryVO 가져오기 (게시판 정보)
		System.out.println("Input data="+vo);
	    Inquiry inquiry = inquiryService.getInquiry(vo.getId());
	    if (inquiry == null) {
	        return "redirect:/admin_inquiry_list"; // 게시물이 없으면 목록으로 이동
	    }

	    // 2. 댓글 추가 및 상태 변경
	    inquiry.setReply(vo.getReply());  // 댓글 저장
	    inquiry.setStatus(1);  // 상태를 1로 변경

	    // 3. 업데이트된 inquiryVO 저장
	    System.out.println("Q&A : " + inquiry);
	    inquiryService.updateInquiry(inquiry);

		
		//inquiryService.updateInquiry(vo);
		
		return "redirect:admin_inquiry_list";
	}

	@RequestMapping("/admin_review_list")
	public String adminReviewList(Model model) {
		// Inquiry 전체 목록 조회
		List<Review> reviewList = reviewService.getAllReviews();
		
		 if (reviewList.isEmpty()) {
	            model.addAttribute("message", "리뷰가 없습니다.");
	        } else {
	            model.addAttribute("reviewList", reviewList);
	        }
		return "admin/review/reviewList";
	}
	
	@PostMapping("/admin_review_detail")
	public String adminReviewDetail(Review vo, Model model) {
		// (1) Inquiry 일련번호를 조건으로 Inquiry 조회
		Review review = reviewService.getReview(vo.getId());
		
		// (2) InquiryVO 속성에 데이터 저장
		model.addAttribute("reviewVO", review);
		
		// (3) InquiryDetail 화면 호출
		return "admin/review/reviewDetail";
	}
	
	@PostMapping("/admin_review_replysave")
	public String adminCommentRepsave(Review vo) {
		
		reviewService.updateReview(vo);
		
		return "redirect:admin_review_list";
	}
	
	/*
	 * 리뷰 리스트 불러오기
	 * 
	 * */
	@GetMapping("/reviewList")
    public String getReviewList(Model model) {
        List<Review> reviewList = reviewService.getAllReviews();
        
        if (reviewList.isEmpty()) {
            model.addAttribute("message", "리뷰가 없습니다.");
        } else {
            model.addAttribute("reviewList", reviewList);
        }
        
        return "admin/review/reviewList";  // Thymeleaf 템플릿 경로
    }
	
	/*
	 * 신고된 뎃글 삭제처리
	 */
	
	@PostMapping("/admin_review_delete")
    public String adminDeleteReview(@RequestParam Long id) {
        try {
            reviewService.deleteReview(id);  // 리뷰 삭제 서비스 호출
            return "redirect:/reviewList";  // 삭제 후 리뷰 리스트로 리다이렉트
        } catch (Exception e) {
            // 에러 처리
            return "redirect:/admin/review/reviewList?error=true";
        }
    }
	
	/*
	 * 제품별 판매실적 화면 표시
	 */

}