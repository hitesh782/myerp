package com.example.erpdemo.Exception;

public class OrderException extends MpsBooksServiceException {
    private static final long serialVersionUID = 1L;

    OrderException(final String message) {
        super(message);
    }

    OrderException(final Throwable cause) {
        super(cause);
    }

    OrderException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
