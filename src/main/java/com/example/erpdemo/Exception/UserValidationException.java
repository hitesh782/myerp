package com.example.erpdemo.Exception;

public class UserValidationException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserValidationException(final String message) {
        super(message);
    }

    public UserValidationException(final Throwable cause) {
        super(cause);
    }

    public UserValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
