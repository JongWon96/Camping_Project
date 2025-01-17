package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.ReservationDetail;
import com.example.demo.persistence.ReservationRepository;


@Service
public class ReservationServiceImpl implements ReservationService {

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

	@Override
	public void updateReservation(Reservation vo) {
		Optional<Reservation> result = reservationRepo.findById(vo.getId());
		
		if (result.isPresent()) {
			Reservation reservation = result.get();
			
			//reservation.setReply(vo.getReply());
			
			reservationRepo.save(reservation);
		}
		
	}

	@Override
	public List<Reservation> getReservationList() {
	
		return reservationRepo.getReservationList();
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









