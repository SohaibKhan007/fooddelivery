package com.fooddelivery.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application. This class handles exceptions
 * thrown by controllers and provides meaningful responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Handles generic exceptions.
	 *
	 * @param e the exception that was thrown
	 * @return ResponseEntity containing the error message and HTTP status
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception e) {
		logger.error("An unexpected error occurred: {}", e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An unexpected error occurred: " + e.getMessage());
	}

	/**
	 * Handles resource not found exceptions.
	 *
	 * @param e the exception that was thrown
	 * @return ResponseEntity containing the error message and HTTP status
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
		logger.warn("Resource not found: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
	}

	/**
	 * Handles user not found exceptions.
	 *
	 * @param e the exception that was thrown
	 * @return ResponseEntity containing the error message and HTTP status
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
		logger.warn("User not found: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
	}

	/**
	 * Handles validation exceptions.
	 *
	 * @param e the exception that was thrown
	 * @return ResponseEntity containing the error message and HTTP status
	 */
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	public ResponseEntity<String> handleValidationException(javax.validation.ConstraintViolationException e) {
		logger.warn("Validation error: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + e.getMessage());
	}
}