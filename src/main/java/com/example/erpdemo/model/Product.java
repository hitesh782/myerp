package com.example.erpdemo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.erpdemo.model.bo.BOWithId;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="products")
public class Product extends BOWithId {

	@NotBlank(message="product name cannot be null")
	private String name;
	
	private String description;
}
