package com.example.demo.service;

import com.example.demo.domain.Product;

import java.util.List;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Product;

public interface ProductService {

	public List<Product> getProducts(Long campingId);

	public Product getProduct(Long productId);

	public Product getProductByRoom(Long campingId, Integer roomNum);

    // 캠핑장에 맞는 3개의 상품을 생성하는 메서드
    void generateProductsForExistingCampings();

    // 캠핑장 ID와 방 타입에 맞는 상품을 찾는 메서드
    Product findByCamping_IdAndRoom(Long campingId, int room);

    // 예약된 방 수를 조회하는 메서드 (특정 날짜에 예약된 방 수)
    int getReservedRoomCount(Long campingId, int room, String checkin, String checkout);

    // 방 수량을 반환하는 메서드 (총 방 수)
    int getRoomCount(Long campingId, int room);  // 추가된 메서드
}
