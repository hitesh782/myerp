package com.example.erpdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.erpdemo.Exception.CartException;
import com.example.erpdemo.Exception.OrderException;
import com.example.erpdemo.model.Order;
import com.example.erpdemo.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/user/{userId}")
	public List<Order> getOrdersByUserId(@PathVariable String userId) {
		return orderService.getOrderByUser(userId);
	}

	@GetMapping("/{orderId}")
	public Order getOrderById(@PathVariable String orderId) throws OrderException {
		return orderService.getOrderById(orderId);
	}

	@PostMapping("/user/{userId}")
	public Order createOrder(@PathVariable String userId) throws CartException {
		return orderService.createOrder(userId);
	}

}
