package com.example.erpdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.erpdemo.Exception.BookNotFoundException;
import com.example.erpdemo.Exception.CartNotFoundException;
import com.example.erpdemo.model.Book;
import com.example.erpdemo.model.Cart;
import com.example.erpdemo.model.CartItem;
import com.example.erpdemo.repository.BookRepository;
import com.example.erpdemo.repository.CartRepository;
import com.example.erpdemo.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private BookRepository bookRepo;

    @Override
    public Cart getCartByUserId(String userId) throws CartNotFoundException {

        Cart cart = cartRepo.findByUserId(userId);

        if (cart == null) {
            throw new CartNotFoundException("cart not found for perticular userId");
        }

        return cart;
    }

    @Override
    public Cart addBookToCart(String userId, String bookId, int quantity)
            throws CartNotFoundException, BookNotFoundException {
        Cart cart = cartRepo.findByUserId(userId);
        Book book = bookRepo.findById(bookId).orElse(null);

        if (cart == null) {
            throw new CartNotFoundException("cart not found for userId");
        } else if (book == null) {
            throw new BookNotFoundException("book is not found");
        }

        if (cart != null && book != null) {
            CartItem cartItem = new CartItem();
            cartItem.setBook(book);
            cartItem.setQuantity(quantity);
            cart.getItems().add(cartItem);

            cartRepo.save(cart);
        }

        return cart;

    }

    @Override
    public Cart removeBookFromCart(String userId, String bookId) throws CartNotFoundException {
        Cart cart = cartRepo.findByUserId(userId);

        if (cart != null) {
            cart.getItems().removeIf(item -> item.getBook().getId().equals(bookId));
            cartRepo.save(cart);
        } else {
            throw new CartNotFoundException("cart not found for given user");
        }

        return cart;

    }

    @Override
    public void deleteCart(String userId) throws CartNotFoundException {
        Cart cart = cartRepo.findByUserId(userId);

        if (cart != null) {
            cart.getItems().clear();
            cartRepo.save(cart);
        } else {
            throw new CartNotFoundException("cart not found for given user");
        }
    }

}
