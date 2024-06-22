package com.example.erpdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.erpdemo.model.Cart;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findByUserId(String UserId);

    Cart deleteByUserId(String userId);
}
