package com.example.erpdemo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.erpdemo.Exception.ProductException;
import com.example.erpdemo.Exception.SearchException;
import com.example.erpdemo.model.Product;
import com.example.erpdemo.model.query.SearchEvent;

public interface ProductService {
	Product saveProduct(Product product) throws ProductException;
	
	Product updateProduct(Product product) throws ProductException;
	
	Page<Product> search(SearchEvent searchEvent,Pageable pageable) throws SearchException;
	
	void deleteProduct(String productId) throws ProductException;
}
