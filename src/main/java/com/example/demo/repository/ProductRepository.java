package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 캠핑장 ID와 방 유형을 통해 Product 찾기
    Product findByCamping_IdAndRoom(Long Id, int room);

    List<Product> findByCamping_Id(Long campingId);
    
}
