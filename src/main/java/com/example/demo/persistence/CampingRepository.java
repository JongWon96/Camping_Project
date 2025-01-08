package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.Camping;

public interface CampingRepository extends JpaRepository<Camping, Long> {

    // 특정 도에 포함된 시군구 목록 가져오기
    @Query("SELECT DISTINCT c.sigungunm FROM Camping c WHERE c.donm = :donm")
    List<String> findSigungunmByDonm(@Param("donm") String donm);
    
    // 도 목록 가져오기
    @Query("SELECT DISTINCT c.donm FROM Camping c")
    List<String> findDistinctDonm();
}
