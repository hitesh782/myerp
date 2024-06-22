package com.example.erpdemo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.erpdemo.model.bo.BOWithId;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BOWithId {

	private String name;

	private String description;

	private String author;

	private double price;

	@JsonProperty("ISBN")
	private String ISBN;

}
