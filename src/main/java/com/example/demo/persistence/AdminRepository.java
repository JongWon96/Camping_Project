package com.example.demo.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Optional<Admin> findByAdminId(String adminId);

}
