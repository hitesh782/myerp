package com.example.erpdemo.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.erpdemo.model.bo.BOWithId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "carts")
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BOWithId {

	private String userId;

	private List<CartItem> items;

}
