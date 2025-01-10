package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Product;
import com.example.demo.persistance.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepo;

	@Override
	public List<Product> getProducts(Long campingId) {
		
		return productRepo.findByCampingId(campingId);
	}

	@Override
	public Product getProduct(Long productId) {
		
		return productRepo.findById(productId).get();
	}

	@Override
	public Product getProductByRoom(Long productId, Integer roomNum) {
		
		return productRepo.findByRoom(productId, roomNum);
	}
	
}
