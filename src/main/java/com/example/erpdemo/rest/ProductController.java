package com.example.erpdemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.erpdemo.Exception.ProductException;
import com.example.erpdemo.Exception.SearchException;
import com.example.erpdemo.model.Product;
import com.example.erpdemo.model.query.SearchEvent;
import com.example.erpdemo.service.ProductService;

@RequestMapping("/products")
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product saveProduct(@RequestBody final Product product) throws ProductException {
		return productService.saveProduct(product);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product updateProduct(@RequestBody final Product product) throws ProductException {
		return productService.updateProduct(product);
	}

	@PostMapping(value = "/criteria-search")
	public Page<Product> search(@RequestBody final SearchEvent search,
			@PageableDefault(size = Integer.MAX_VALUE) final Pageable pageable) throws SearchException {
		return productService.search(search, pageable);
	}

	@DeleteMapping(value = "/{productId}")
	public void deleteProduct(@PathVariable final String productId) throws ProductException {
		productService.deleteProduct(productId);
	}

}
