package com.example.erpdemo.Exception;

public class BookValidationException extends BookException {

    private static final long serialVersionUID = 1L;

    public BookValidationException(final String message) {
        super(message);
    }

    BookValidationException(final Throwable cause) {
        super(cause);
    }

    BookValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
