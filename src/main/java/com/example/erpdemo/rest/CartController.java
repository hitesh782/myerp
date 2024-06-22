package com.example.erpdemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.erpdemo.Exception.BookException;
import com.example.erpdemo.Exception.CartException;
import com.example.erpdemo.model.Cart;
import com.example.erpdemo.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping("/user/{userId}")
	public Cart getCartByUserId(@PathVariable String userId) throws CartException {
		return cartService.getCartByUserId(userId);
	}

	@PostMapping("/user/{userId}/add/{bookId}")
	public Cart addBookToCart(@PathVariable String userId, @PathVariable String bookId, @RequestParam int quantity)
			throws CartException, BookException {
		return cartService.addBookToCart(userId, bookId, quantity);
	}

	@PostMapping("/user/{userId}/remove/{bookId}")
	public Cart removeBookFromCart(@PathVariable String userId, @PathVariable String bookId) throws CartException {
		return cartService.removeBookFromCart(userId, bookId);
	}

	@DeleteMapping("/user/{userId}")
	public void deleteCart(@PathVariable String userId) throws CartException {
		cartService.deleteCart(userId);
	}

}
