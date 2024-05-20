package com.example.erpdemo.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.erpdemo.Exception.MpsBooksErrorResponse;
import com.example.erpdemo.Exception.ProductException;
import com.example.erpdemo.Exception.ProductNotFoundException;
import com.example.erpdemo.Exception.ProductValidationException;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleProductException(final ProductException ex){
		return new ResponseEntity<>(
					new MpsBooksErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR
				);
	}
	
	@ExceptionHandler(ProductValidationException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleProductValidationException(final ProductValidationException ex){
		return new ResponseEntity<>(
					new MpsBooksErrorResponse(HttpStatus.PRECONDITION_FAILED.value(),ex.getMessage()),
					HttpStatus.PRECONDITION_FAILED
				);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<MpsBooksErrorResponse> handleProductNotFoundException(final ProductNotFoundException ex){
		return new ResponseEntity<>(
					new MpsBooksErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage()),
					HttpStatus.NOT_FOUND
				);
	}
}
