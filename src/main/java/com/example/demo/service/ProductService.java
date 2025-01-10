package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Product;

public interface ProductService {

	public List<Product> getProducts(Long campingId);
	
	public Product getProduct(Long productId);
	
	public Product getProductByRoom(Long productId, Integer roomNum);
}
 