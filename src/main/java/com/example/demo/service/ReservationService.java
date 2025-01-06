package com.example.demo.service;

import com.example.demo.domain.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation saveReservation(Reservation reservation);
    List<Reservation> getReservationsByMemberId(Long memberId);
}
