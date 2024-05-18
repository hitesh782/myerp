package com.example.erpdemo.Exception;

public class SearchException extends MpsBooksServiceException{
	private static final long serialVersionUID = 1L;

	public SearchException(final String message) {
		super(message);
	}

	SearchException(final Throwable cause) {
		super(cause);
	}

	SearchException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
