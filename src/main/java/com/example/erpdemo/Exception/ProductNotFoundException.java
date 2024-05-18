package com.example.erpdemo.Exception;

public class ProductNotFoundException extends ProductException{
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(final String message) {
		super(message);
	}

	ProductNotFoundException(final Throwable cause) {
		super(cause);
	}

	ProductNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
