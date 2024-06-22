package com.example.erpdemo.Exception;

public class CartNotFoundException extends CartException {
    private static final long serialVersionUID = 1L;

    public CartNotFoundException(final String message) {
        super(message);
    }

    CartNotFoundException(final Throwable cause) {
        super(cause);
    }

    CartNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
