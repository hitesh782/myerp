package com.example.erpdemo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.erpdemo.Exception.ProductException;
import com.example.erpdemo.Exception.ProductNotFoundException;
import com.example.erpdemo.Exception.ProductValidationException;
import com.example.erpdemo.Exception.SearchException;
import com.example.erpdemo.config.LF;
import com.example.erpdemo.config.QueryBuilder;
import com.example.erpdemo.model.Product;
import com.example.erpdemo.model.query.SearchEvent;
import com.example.erpdemo.repository.ProductRepo;
import com.example.erpdemo.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	@Override
	public Product saveProduct(Product product) throws ProductException {
		if(StringUtils.isEmpty(product.getName())) {
			log.info("product name is getting null");
			throw new ProductValidationException("product name is null");
		}
		return this.productRepo.save(product);
	}

	@Override
	public Product updateProduct(Product product) throws ProductException {
		if(StringUtils.isEmpty(product.getId())) {
			log.info("product id is null");
			throw new ProductValidationException("product id is null");
		}
		
		Optional<Product> productOptional = this.productRepo.findById(product.getId());
		if(productOptional.isPresent()) {
			Product oldProduct = productOptional.get();
			Product newProduct = this.productRepo.save(product);
			return newProduct;
		}
		else {
			log.info("product not find with given id");
			throw new ProductNotFoundException("product with given id is not found");
		}
	}

	@Override
	public Page<Product> search(SearchEvent search, Pageable pageable) throws SearchException {
		Query query = null;
		query = QueryBuilder.buildSearchQuery(search,pageable);
		
		query.collation(Collation.of("en").strength(Collation.ComparisonLevel.secondary()));
		
		log.info("query for discount search is: {}",query);
		
		return new PageImpl<>(this.mongoOperations.find(query, Product.class),pageable,this.mongoOperations.count(query, Product.class));
	}

	@Override
	public void deleteProduct(String productId) throws ProductException {
		Optional<Product> productOptional = this.productRepo.findById(productId);

		if(productOptional.isPresent()) {
			Product product = productOptional.get();
			product.setDeleted(true);
			this.productRepo.save(product);
		}
		else {
			log.info(LF.format("product with id: {} is not present",productId));
//			log.info("product with given productId is not found");
			throw new ProductNotFoundException("product with given id is not found");
		}
	}

}
