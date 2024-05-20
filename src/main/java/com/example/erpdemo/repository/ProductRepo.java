package com.example.erpdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.erpdemo.model.Product;

public interface ProductRepo extends MongoRepository<Product,String> {

	Product findByIdAndActive(String id,boolean active);
	
	Product findByIdAndDeleted(String id,boolean deleted);
}
