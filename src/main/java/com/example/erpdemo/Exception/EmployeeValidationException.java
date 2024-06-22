package com.example.erpdemo.Exception;

public class EmployeeValidationException extends EmployeeException {
    private static final long serialVersionUID = 1L;

    public EmployeeValidationException(final String message) {
        super(message);
    }

    EmployeeValidationException(final Throwable cause) {
        super(cause);
    }

    EmployeeValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
