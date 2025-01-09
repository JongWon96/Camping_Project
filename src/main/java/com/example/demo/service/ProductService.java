package com.example.demo.service;

import com.example.demo.domain.Product;

public interface ProductService {

    // 캠핑장에 맞는 3개의 상품을 생성하는 메서드
    void generateProductsForExistingCampings();

    // 방 수량을 차감하는 메서드
    void reduceRoomCount(Long id, int room);

    // 방 수량을 복구하는 메서드
    void restoreRoomCount(Long id, int room);

    // 캠핑장 ID와 방 타입에 맞는 상품을 찾는 메서드
    Product findByCamping_IdAndRoom(Long campingId, int room);

    // 예약된 방 수를 조회하는 메서드 (특정 날짜에 예약된 방 수)
    int getReservedRoomCount(Long campingId, int room, String checkin, String checkout);

    // 방 수량을 반환하는 메서드 (총 방 수)
    int getRoomCount(Long campingId, int room);  // 추가된 메서드
}
