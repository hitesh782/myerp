package com.example.erpdemo.Exception;

public class BookException extends MpsBooksServiceException {
    private static final long serialVersionUID = 1L;

    BookException(final String message) {
        super(message);
    }

    BookException(final Throwable cause) {
        super(cause);
    }

    BookException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
