package com.example.erpdemo.Exception;

public class EmployeeException extends MpsBooksServiceException {
    private static final long serialVersionUID = 1L;

    EmployeeException(final String message) {
        super(message);
    }

    EmployeeException(final Throwable cause) {
        super(cause);
    }

    EmployeeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
