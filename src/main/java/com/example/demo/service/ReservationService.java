package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.ReservationDetail;

@Service
public interface ReservationService {

	void insertReservation(Reservation Reservation);

	Reservation getReservation(long id);

	List<Reservation> getReservationList();
	
	List<Reservation> getAllReservation();

	void updateReservation(Reservation vo);

	Optional<Reservation> findById(long id);
	
	List<ReservationDetail> getListReservationById(long id);

	void updateReservationResult(long id, String i);

	Reservation getReservationById(long long1);
	
}