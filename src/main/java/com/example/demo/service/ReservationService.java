package com.example.demo.service;

import com.example.demo.domain.Reservation;

public interface ReservationService {
    void save(Reservation reservation);  // 예약 저장
    Reservation findById(Long id);       // 예약 조회
    void cancelReservation(Long reservationId);  // 예약 취소
}
