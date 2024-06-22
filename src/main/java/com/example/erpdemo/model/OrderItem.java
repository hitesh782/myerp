package com.example.erpdemo.model;

import com.example.erpdemo.model.bo.BOWithId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BOWithId {

	private String bookId;

	private int quantity;

	private double unitPrice;

	private double totalPrice;

}
