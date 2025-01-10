package com.example.demo.persistence;

import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 캠핑장 ID와 방 유형을 통해 Product 찾기
    Product findByCamping_IdAndRoom(Long Id, int room);

    List<Product> findByCamping_Id(Long campingId);
    
}
