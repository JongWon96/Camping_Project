package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Member;
import com.example.demo.domain.Product;
import com.example.demo.domain.Reservation;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReservationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReservationContoller {
	
	@Autowired 
	private ReservationService reservationService;
	@Autowired 
	private ProductService productService;

	@GetMapping("/reservationpage")
	private String reservationPage(@RequestParam("productid") Long productId, 
									@RequestParam("room") Integer roomNum, Model model) {
		
		Product product = productService.getProductByRoom(productId, roomNum);
		
		model.addAttribute("product", product);
		
		return "tmp/reservationPage";
	}
	
	@PostMapping("/reservation_success")
	public String reservationSuccess(@RequestParam("id") Long id, 
										Reservation reservation, 
										HttpSession session) {
		
		Member loginUser = (Member)session.getAttribute("loginUser");  //추후 변경
		
		String url = "";
		
		if(loginUser == null) {	
			url="member/login";  //추후 변경
		} else {
			reservation.setMember(loginUser);
			
			Product p = new Product();
			
			p.setId(id);
			reservation.setProduct(p);
			
			
			url = "mypage/reservation"; // 추후 변경
		}
		
		return url;
	}
}
