package com.example.erpdemo.service;

import java.util.List;

import com.example.erpdemo.Exception.CartException;
import com.example.erpdemo.Exception.OrderException;
import com.example.erpdemo.model.Order;

public interface OrderService {
    List<Order> getOrderByUser(String userId);

    Order getOrderById(String orderId) throws OrderException;

    Order createOrder(String userId) throws CartException;
}
