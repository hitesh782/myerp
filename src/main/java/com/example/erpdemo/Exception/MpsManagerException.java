package com.example.erpdemo.Exception;

public class MpsManagerException extends Exception {
    private static final long serialVersionUID = 1L;

    public MpsManagerException(final String message) {
        super(message);
    }

    MpsManagerException(final Throwable cause) {
        super(cause);
    }

    MpsManagerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
