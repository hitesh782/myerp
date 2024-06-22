package com.example.erpdemo.Exception;

public class BookNotFoundException extends BookException {
    private static final long serialVersionUID = 1L;

    public BookNotFoundException(final String message) {
        super(message);
    }

    public BookNotFoundException(final Throwable cause) {
        super(cause);
    }

    public BookNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
