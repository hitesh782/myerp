package com.example.erpdemo.service;

import com.example.erpdemo.Exception.BookException;
import com.example.erpdemo.Exception.CartException;
import com.example.erpdemo.model.Cart;

public interface CartService {
    Cart getCartByUserId(String userId) throws CartException;

    Cart addBookToCart(String userId, String bookId, int quantity) throws CartException, BookException;

    Cart removeBookFromCart(String userId, String bookId) throws CartException;

    void deleteCart(String userId) throws CartException;
}
