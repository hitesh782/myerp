package com.example.erpdemo.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.erpdemo.Exception.BookException;
import com.example.erpdemo.Exception.BookNotFoundException;
import com.example.erpdemo.Exception.BookValidationException;
import com.example.erpdemo.Exception.CartException;
import com.example.erpdemo.Exception.CartNotFoundException;
import com.example.erpdemo.Exception.EmployeeException;
import com.example.erpdemo.Exception.EmployeeNotFoundException;
import com.example.erpdemo.Exception.EmployeeValidationException;
import com.example.erpdemo.Exception.MpsBooksErrorResponse;
import com.example.erpdemo.Exception.MpsBooksServiceException;
import com.example.erpdemo.Exception.MpsManagerException;
import com.example.erpdemo.Exception.OrderException;
import com.example.erpdemo.Exception.OrderNotFoundException;
import com.example.erpdemo.Exception.ProductException;
import com.example.erpdemo.Exception.ProductNotFoundException;
import com.example.erpdemo.Exception.ProductValidationException;
import com.example.erpdemo.Exception.UserException;
import com.example.erpdemo.Exception.UserNotFoundException;
import com.example.erpdemo.Exception.UserValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProductException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleProductException(final ProductException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ProductValidationException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleProductValidationException(final ProductValidationException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage()),
				HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleProductNotFoundException(final ProductNotFoundException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BookException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleBookException(final BookException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BookValidationException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleBookValidationException(final BookValidationException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage()),
				HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleBookNotFoundException(final BookNotFoundException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleUserException(final UserException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserValidationException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleUserValidationException(final UserValidationException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage()),
				HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleUserNotFoundException(final UserNotFoundException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CartException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleCartException(final CartException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleCartNotFoundException(final CartNotFoundException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(OrderException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleOrderException(final OrderException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleOrderNotFoundException(final OrderNotFoundException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmployeeException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleEmployeeException(final EmployeeException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(EmployeeValidationException.class)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	public ResponseEntity<MpsBooksErrorResponse> handleEmployeeValidationException(
			final EmployeeValidationException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage()),
				HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<MpsBooksErrorResponse> handleEmployeeNotFoundException(final EmployeeNotFoundException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MpsManagerException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleManagerException(final MpsManagerException ex) {
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage()),
				HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		// return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(
				new MpsBooksErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}
}
