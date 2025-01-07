package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.domain.Reservation;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReservationService;

import jakarta.transaction.Transactional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public void save(Reservation reservation) {
        // 방 수량 차감
        productService.reduceRoomCount(reservation.getProduct().getCamping().getId(), reservation.getProduct().getRoom());
        reservationRepository.save(reservation); // 예약 저장
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null) {
            // 방 수량 복구
            productService.restoreRoomCount(reservation.getProduct().getCamping().getId(), reservation.getProduct().getRoom());
            reservationRepository.delete(reservation); // 예약 취소
        }
    }
}
