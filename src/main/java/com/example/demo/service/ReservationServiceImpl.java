package com.example.demo.service;

import java.sql.Date;
import java.util.List;

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

    // 예약 기간이 끝났는지 확인하고 후기 작성이 가능한지 확인
    public boolean isReviewAvailable(Long campingId, Long memberId) {
        Reservation reservation = reservationRepository.findByCamping_IdAndMember_Id(campingId, memberId);

        if (reservation == null) {
            throw new RuntimeException("No reservation found for the given member and camping.");
        }

        // 예약 종료일이 현재 날짜보다 이전이면 후기 작성 가능
        Date currentDate = new Date(System.currentTimeMillis());
        return reservation.getCheckout().before(currentDate);  // checkout이 현재 날짜 이전이면 true
    }

    @Override
    public Reservation getReservationByIds(Long campingId, Long memberId) {
        // campingId와 memberId로 예약 정보를 조회
        return reservationRepository.findByCamping_IdAndMember_Id(campingId, memberId);
    }

    // 날짜별 예약 가능 방 수 조회
    @Override
    public int getRemainingRooms(Long campingId, int room, Date checkinDate, Date checkoutDate) {
        // 특정 날짜 범위에 예약된 수를 조회
        List<Reservation> reservations = reservationRepository.findReservationsByCampingIdAndRoomAndDateRange(campingId, room, checkinDate, checkoutDate);

        // 전체 방 수에서 예약된 방 수를 뺀 남은 방 수 계산
        int totalRoomCount = productService.getRoomCount(campingId, room);
        int reservedRooms = reservations.size(); // 예약된 방 수

        return totalRoomCount - reservedRooms; // 남은 방 수
    }

    // 예약된 방 목록을 날짜 범위로 조회
    @Override
    public List<Reservation> findReservationsByCampingIdAndRoomAndDateRange(Long campingId, int room, Date checkinDate, Date checkoutDate) {
        return reservationRepository.findReservationsByCampingIdAndRoomAndDateRange(campingId, room, checkinDate, checkoutDate);
    }
}
