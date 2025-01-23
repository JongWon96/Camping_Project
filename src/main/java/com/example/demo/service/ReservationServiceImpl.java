package com.example.demo.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.persistence.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Member;
import com.example.demo.domain.Reservation;
import com.example.demo.domain.ReservationDetail;


import jakarta.transaction.Transactional;
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ProductService productService;

    // 예약 저장 처리 (방 수량 차감하지 않음)
    @Transactional
    @Override
    public void save(Reservation reservation) {
        reservationRepository.save(reservation); // 예약을 저장한 후, 방 수는 차감되지 않음
    }

    // 예약 ID로 예약 정보 찾기
    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }


    @Override
    public void cancelReservation(Long reservationId, Member member) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null) {
            reservationRepository.delete(reservation); // 예약 취소
        }
    }
	@Autowired
	private ReservationRepository reservationRepo;

	@Override
    public void insertReservation(Reservation Reservation) {

		reservationRepo.save(Reservation);
	}

	@Override
	public Reservation getReservation(long id) {


		Optional<Reservation> reservation = reservationRepo.findById(id);
	    return reservation.orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

		//return reservationRepo.findById(id).get();
	}

	public Optional<Reservation> findById(long id){
		return reservationRepo.findById(id);
	}

	@Override
	public List<Reservation> getAllReservation() {

		return reservationRepo.findAll();
	}

    // 예약 기간이 끝났는지 확인하고 후기 작성이 가능한지 확인
    @Override
    public boolean isReviewAvailable(Long campingId, Long memberId) {
        List<Reservation> reservations = reservationRepository.findByCamping_IdAndMember_Id(campingId, memberId);

        if (reservations.isEmpty()) {
            throw new RuntimeException("No reservation found for the given member and camping.");
        }

        // 예약 종료일이 현재 날짜보다 이전이면 후기 작성 가능
        Date currentDate = new Date(System.currentTimeMillis());

        // 예약 리스트에서 가장 최근의 예약을 찾기
        Reservation latestReservation = reservations.get(reservations.size() - 1); // 리스트가 오름차순으로 정렬되어 있다고 가정

        return latestReservation.getCheckout().before(currentDate);  // checkout이 현재 날짜 이전이면 true
    }
	@Override
	public void updateReservation(Reservation vo) {
		Optional<Reservation> result = reservationRepo.findById(vo.getId());

		if (result.isPresent()) {
			Reservation reservation = result.get();

			//reservation.setReply(vo.getReply());

			reservationRepo.save(reservation);
		}

	}

    // 캠핑장 ID와 회원 ID로 예약 정보 조회 (여러 개의 예약을 반환)
    @Override
    public List<Reservation> getReservationByIds(Long campingId, Long memberId) {
        return reservationRepository.findByCamping_IdAndMember_Id(campingId, memberId);
    }
	@Override
	public List<Reservation> getReservationList() {

		return reservationRepo.getReservationList();
	}

    // 특정 날짜 범위에 예약된 방 수 조회
    @Override
    public int getRemainingRooms(Long campingId, int room, Date checkinDate, Date checkoutDate) {
        // 예약된 방 수를 조회
        List<Reservation> reservations = reservationRepository.findReservationsByCampingIdAndRoomAndDateRange(campingId, room, checkinDate, checkoutDate);

        // 전체 방 수에서 예약된 방 수를 뺀 남은 방 수 계산
        int totalRoomCount = productService.getRoomCount(campingId, room);
        int reservedRooms = reservations.size(); // 예약된 방 수

        return totalRoomCount - reservedRooms; // 남은 방 수
    }



    // 날짜 범위 내 예약된 방 목록을 조회
    @Override
    public List<Reservation> findReservationsByCampingIdAndRoomAndDateRange(Long campingId, int room, Date checkinDate, Date checkoutDate) {
        return reservationRepository.findReservationsByCampingIdAndRoomAndDateRange(campingId, room, checkinDate, checkoutDate);
    }

    @Override
    public List<Reservation> findReservationsByMember(Member member) {
        return reservationRepository.findByMember_Id(member.getId());
    }
	@Override
	public List<ReservationDetail> getListReservationById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateReservationResult(long id, String i) {
		Optional<Reservation> result = reservationRepo.findById(id);

		if (result.isPresent()) {
			Reservation reservation = result.get();

			reservation.setResult(i);
			reservationRepo.save(reservation);
		}

	}
	public Reservation getReservationById(long id) {

		Optional<Reservation> r =  reservationRepo.findById(id);
		if (r.isPresent()) {
			return r.get();
		} else {
			return null;
		}
	}
}










