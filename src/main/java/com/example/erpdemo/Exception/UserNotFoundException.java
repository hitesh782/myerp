package com.example.erpdemo.Exception;

public class UserNotFoundException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(final String message) {
        super(message);
    }

    public UserNotFoundException(final Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
