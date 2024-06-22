package com.example.erpdemo.Exception;

public class UserException extends MpsBooksServiceException {
    private static final long serialVersionUID = 1L;

    UserException(final String message) {
        super(message);
    }

    UserException(final Throwable cause) {
        super(cause);
    }

    UserException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
