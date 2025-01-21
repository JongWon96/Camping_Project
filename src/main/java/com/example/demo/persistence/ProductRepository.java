package com.example.demo.persistence;

import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 캠핑장 ID와 방 유형을 통해 Product 찾기
    Product findByCamping_IdAndRoom(Long Id, int room);

    List<Product> findByCamping_Id(Long campingId);
    
    
    Product findByCamping_idAndRoom(Long campingId, Integer roomnum);

    

	@Query("SELECT p FROM Product p "
				+ " WHERE p.camping.id = %?1%"
				+ " ORDER BY p.room ASC")
	List<Product> findByCampingId(Long campingId);
}
