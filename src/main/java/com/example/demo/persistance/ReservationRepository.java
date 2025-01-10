package com.example.demo.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

}
