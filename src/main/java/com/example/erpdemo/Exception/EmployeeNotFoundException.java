package com.example.erpdemo.Exception;

public class EmployeeNotFoundException extends EmployeeException {
    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(final String message) {
        super(message);
    }

    EmployeeNotFoundException(final Throwable cause) {
        super(cause);
    }

    EmployeeNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
