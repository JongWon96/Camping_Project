package com.example.demo.repository;


	import com.example.demo.domain.Reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;

	@Repository
	public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	    // 예약 관련 커스텀 쿼리를 추가할 수 있습니다.
	    // 예: 특정 회원의 예약 내역 조회
	    List<Reservation> findByMemberId(Long memberId);
	}


