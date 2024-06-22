package com.example.erpdemo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.erpdemo.Exception.CartNotFoundException;
import com.example.erpdemo.Exception.OrderNotFoundException;
import com.example.erpdemo.model.Cart;
import com.example.erpdemo.model.CartItem;
import com.example.erpdemo.model.Order;
import com.example.erpdemo.model.OrderItem;
import com.example.erpdemo.repository.CartRepository;
import com.example.erpdemo.repository.OrderRepository;
import com.example.erpdemo.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private CartRepository cartRepo;

    @Override
    public List<Order> getOrderByUser(String userId) {
        return orderRepo.findByUserId(userId);
    }

    @Override
    public Order createOrder(String userId) throws CartNotFoundException {
        Cart cart = cartRepo.findByUserId(userId);

        if (cart == null) {
            throw new CartNotFoundException("cart is not found for userId");
        }

        Order order = new Order();

        order.setUserId(userId);
        order.setOrderDate(new Date());

        double totalPrice = 0;

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBookId(cartItem.getBook().getId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getBook().getPrice());
            orderItem.setTotalPrice(orderItem.getQuantity() * orderItem.getUnitPrice());

            order.getOrderItems().add(orderItem);

        }

        order.setTotalPrice(totalPrice);

        return orderRepo.save(order);

    }

    @Override
    public Order getOrderById(String orderId) throws OrderNotFoundException {
        Optional<Order> orderOptional = orderRepo.findById(orderId);

        if (orderOptional.isPresent()) {
            return orderOptional.get();
        }

        else {
            throw new OrderNotFoundException("Order with given is not present");
        }
    }

}
