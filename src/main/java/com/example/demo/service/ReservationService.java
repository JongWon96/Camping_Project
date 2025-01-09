package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.domain.Reservation;
import java.sql.Date;
import java.util.List;

public interface ReservationService {
    void save(Reservation reservation);  // 예약 저장
    Reservation findById(Long id);       // 예약 조회
    void cancelReservation(Long reservationId);  // 예약 취소
    boolean isReviewAvailable(Long campingId, Long memberId); // 후기 작성 가능 여부
    Reservation getReservationByIds(Long campingId, Long memberId); // campingId와 memberId로 예약 조회
    
    // 추가된 메서드: 날짜별 예약 가능 방 수 조회
    int getRemainingRooms(Long campingId, int room, Date checkinDate, Date checkoutDate); // 날짜별 남은 방 개수 조회
    
    // 추가된 메서드: 특정 캠핑장, 방, 날짜 범위에 대한 예약 조회
    List<Reservation> findReservationsByCampingIdAndRoomAndDateRange(Long campingId, int room, Date checkinDate, Date checkoutDate);

}
