package com.example.erpdemo.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.erpdemo.model.bo.BOWithId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BOWithId {

	private String userId;

	private List<OrderItem> orderItems;

	private double totalPrice;

	private Date orderDate;

}
