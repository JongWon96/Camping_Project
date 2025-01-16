package com.example.demo.controller;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Member;
import com.example.demo.domain.Product;
import com.example.demo.domain.Reservation;
import com.example.demo.service.CampingService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReservationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReservationContoller {

	@Autowired
	private ReservationService reservationService;
	@Autowired
	private ProductService productService;
	@Autowired
	CampingService campingService;

	@GetMapping("/reservationpage")
	private String reservationPage(@RequestParam("campingid") Long campingId, @RequestParam("room") Integer roomNum,
			Model model) {

		Product product = productService.getProductByRoom(campingId, roomNum);

		// 다른 방 두개
		List<Product> otherRooms = productService.getProducts(campingId);

		for (int a = 0; a < 3; a++) {
			if (otherRooms.get(a).getRoom() == roomNum) {
				otherRooms.remove(a);
				break;
			}
		}

		// model.addAttribute("otherRooms", otherRooms);
//		for (int a = 1; a < otherRooms.size()+1; a++) {
//			for (Product otherRoom : otherRooms) {
//				a = (int) otherRoom.getPrice().doubleValue();
//
//				model.addAttribute("otherRoom1", otherRoom);
//				model.addAttribute("price1", price1);
//			}
//		}
		Product otherRoom1 = otherRooms.get(0);
		int price1 = (int)otherRoom1.getPrice().doubleValue();
		
		model.addAttribute("otherRoom1", otherRoom1);
		NumberFormat numberFormat1 = NumberFormat.getNumberInstance(Locale.KOREA);
		String formattedPrice1 = numberFormat1.format(price1);
		model.addAttribute("price1", formattedPrice1);
		
		Product otherRoom2 = otherRooms.get(1);
		int price2 = (int)otherRoom2.getPrice().doubleValue();
		
		model.addAttribute("otherRoom2", otherRoom2);
		NumberFormat numberFormat2 = NumberFormat.getNumberInstance(Locale.KOREA);
		String formattedPrice2 = numberFormat2.format(price2);
		model.addAttribute("price2", formattedPrice2);
		
		model.addAttribute("product", product);
		// 가격 소수점 뒤 .0 제거
		int price = (int) product.getPrice().doubleValue();
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
		String formattedPrice = numberFormat.format(price);

//		for (Product otherRoom : otherRooms) {
//			String tmpPrice = (int)otherRoom.getPrice();
//			otherRoom.setPrice(Double.parseDouble(tmpPrice));
//		}

		model.addAttribute("price", formattedPrice);
		model.addAttribute("campingid", campingId);

		return "Camping/reservationPage";
	}

	@PostMapping("/reservation_success") // 임시 메소드(삭제 혹은 변경)
	public String reservationSuccess(@RequestParam("id") Long id, Reservation reservation, HttpSession session) {

		Member loginUser = (Member) session.getAttribute("loginUser"); // 추후 변경

		String url = "";

		if (loginUser == null) {
			url = "member/login"; // 추후 변경
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
