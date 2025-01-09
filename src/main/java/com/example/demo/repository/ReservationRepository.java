package com.example.demo.repository;

import com.example.demo.domain.Reservation;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 특정 회원의 예약 내역 조회
    List<Reservation> findByMember_Id(Long memberId);

    // 캠핑장 ID와 회원 ID로 예약 조회
    @Query("SELECT r FROM Reservation r WHERE r.product.camping.id = :campingId AND r.member.id = :memberId")
    Reservation findByCamping_IdAndMember_Id(@Param("campingId") Long campingId, 
                                              @Param("memberId") Long memberId);

    // 캠핑장 ID, 방, 날짜 범위에 따른 예약 조회
    @Query("SELECT r FROM Reservation r WHERE r.product.camping.id = :campingId " +
            "AND r.product.room = :room AND r.checkin < :checkout AND r.checkout > :checkin")
    List<Reservation> findReservationsByCampingIdAndRoomAndDateRange(
            @Param("campingId") Long campingId,
            @Param("room") int room,
            @Param("checkin") Date checkin,
            @Param("checkout") Date checkout);
}
