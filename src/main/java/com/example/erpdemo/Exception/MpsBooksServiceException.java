package com.example.erpdemo.Exception;

public class MpsBooksServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public MpsBooksServiceException(final String message) {
		super(message);
	}

	MpsBooksServiceException(final Throwable cause) {
		super(cause);
	}

	MpsBooksServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
