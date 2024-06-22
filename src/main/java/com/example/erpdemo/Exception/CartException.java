package com.example.erpdemo.Exception;

public class CartException extends MpsBooksServiceException {
    private static final long serialVersionUID = 1L;

    CartException(final String message) {
        super(message);
    }

    CartException(final Throwable cause) {
        super(cause);
    }

    CartException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
