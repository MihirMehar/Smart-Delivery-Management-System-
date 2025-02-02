package com.orderservices.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private Environment environment;

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(OrderNotFoundException exception) {
		ApiResponse api = new ApiResponse();
		api.setStatus(HttpStatus.NOT_FOUND);
		api.setMessage(exception.getMessage());
		return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(Exception exception) {
		ApiResponse api = new ApiResponse();
		api.setStatus(HttpStatus.BAD_REQUEST);
		api.setMessage(environment.getProperty(exception.getMessage()));
		return new ResponseEntity<>(api, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationExcpetion(MethodArgumentNotValidException exception) {
		ApiResponse api = new ApiResponse();
		api.setStatus(HttpStatus.BAD_REQUEST);

		StringBuilder errors = new StringBuilder();

		exception.getBindingResult().getFieldErrors().forEach(error -> {
			errors.append(error.getField()).append(":").append(error.getDefaultMessage()).append(":");
		});
		api.setMessage(errors.toString());
		return new ResponseEntity<ApiResponse>(api, HttpStatus.BAD_REQUEST);
	}

}
