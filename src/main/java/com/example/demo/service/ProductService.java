package com.example.demo.service;


import com.example.demo.domain.Product;

public interface ProductService {

    // 캠핑장에 맞는 3개의 상품을 생성하는 메서드
    void generateProductsForExistingCampings();
    
    public void reduceRoomCount(Long id, int room);

    public void restoreRoomCount(Long id, int room);
    
    public Product  findByCamping_IdAndRoom(Long campingId, int room); // 변경된 이름
}
    

