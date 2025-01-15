package com.example.demo.service;

import java.sql.Date;
import java.util.List;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.Member;

public interface ReservationService {

    // 예약 저장 처리
    void save(Reservation reservation);

    // 예약 ID로 예약 정보 찾기
    Reservation findById(Long id);

    // 예약 취소 처리
    void cancelReservation(Long reservationId, Member member);

    // 예약 기간이 끝났는지 확인하고 후기 작성이 가능한지 확인
    boolean isReviewAvailable(Long campingId, Long memberId);

    // 캠핑장 ID와 회원 ID로 예약 정보 조회
    List<Reservation> getReservationByIds(Long campingId, Long memberId);

    // 특정 날짜 범위에 예약된 방 수 조회
    int getRemainingRooms(Long campingId, int room, Date checkinDate, Date checkoutDate);

    // 날짜 범위 내 예약된 방 목록을 조회
    List<Reservation> findReservationsByCampingIdAndRoomAndDateRange(Long campingId, int room, Date checkinDate, Date checkoutDate);

    // 회원의 예약 내역 조회
    List<Reservation> findReservationsByMember(Member member);


}
