package com.example.erpdemo.Exception;

public class OrderNotFoundException extends OrderException {
    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(final String message) {
        super(message);
    }

    OrderNotFoundException(final Throwable cause) {
        super(cause);
    }

    OrderNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
