package com.fooddelivery.exception;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit test for {@link GlobalExceptionHandler}.
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    /**
     * Setup method to initialize the GlobalExceptionHandler before each test.
     */
    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    /**
     * Tests handling of generic exceptions.
     */
    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Something went wrong");

        ResponseEntity<String> response = globalExceptionHandler.handleGenericException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred: Something went wrong", response.getBody());
    }

    /**
     * Tests handling of resource not found exceptions.
     */
    @Test
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");

        ResponseEntity<String> response = globalExceptionHandler.handleResourceNotFoundException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found: Resource not found", response.getBody());
    }

    /**
     * Tests handling of user not found exceptions.
     */
    @Test
    void testHandleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("User not found");

        ResponseEntity<String> response = globalExceptionHandler.handleUserNotFoundException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found: User not found", response.getBody());
    }

    /**
     * Tests handling of validation exceptions.
     */
    @Test
    void testHandleValidationException() {
        ConstraintViolationException exception = new ConstraintViolationException("Invalid input", null);

        ResponseEntity<String> response = globalExceptionHandler.handleValidationException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation error: Invalid input", response.getBody());
    }
}
