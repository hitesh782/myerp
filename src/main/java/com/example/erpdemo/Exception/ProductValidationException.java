package com.example.erpdemo.Exception;

public class ProductValidationException extends ProductException {
	private static final long serialVersionUID = 1L;

	public ProductValidationException(final String message) {
		super(message);
	}

	ProductValidationException(final Throwable cause) {
		super(cause);
	}

	ProductValidationException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
