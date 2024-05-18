package com.example.erpdemo.Exception;

public class ProductException extends MpsBooksServiceException {

	private static final long serialVersionUID = 1L;

	ProductException(final String message) {
		super(message);
	}

	ProductException(final Throwable cause) {
		super(cause);
	}

	ProductException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
