package com.example.demo.service;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 캠핑장에 맞는 3개의 상품을 생성하는 메서드
    @Override
    public void generateProductsForCamping(Camping camping) {
        // 작은 방 가격 생성
        Double basePrice = generateRandomPrice(); // 작은 방 가격 (랜덤)

        // 각 방에 대해 상품 생성 (1: 작은 방, 2: 중간 방, 3: 큰 방)
        for (int room = 1; room <= 3; room++) {
            Product product = new Product();
            product.setCamping(camping);
            product.setRoom(room);
            
            // 가격 설정
            if (room == 1) {
                product.setPrice(basePrice); // 작은 방 가격
            } else if (room == 2) {
                product.setPrice(basePrice * 1.2); // 중간 방 가격 (1.2배)
            } else if (room == 3) {
                product.setPrice(basePrice * 1.4); // 큰 방 가격 (1.4배)
            }
            
          
            // 상품 저장
            productRepository.save(product);
        }
    }

    // 10만 ~ 20만 사이의 랜덤 가격 생성
    private Double generateRandomPrice() {
        Random random = new Random();
        int minPrice = 100000;
        int maxPrice = 200000;
        return (minPrice + (maxPrice - minPrice) * random.nextDouble());
    }


}
