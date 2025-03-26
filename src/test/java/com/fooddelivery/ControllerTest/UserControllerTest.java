package com.fooddelivery.ControllerTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.fooddelivery.Controller.UserController;
import com.fooddelivery.exception.UserNotFoundException;
import com.fooddelivery.model.User;
import com.fooddelivery.service.UserService;

/**
 * Unit tests for {@link UserController}.
 */
class UserControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test case for successfully creating a user.
	 */
	@Test
	void testCreateUser_Successful() {
		// Arrange
		User user = new User();
		user.setId(1L);
		user.setName("John Doe");

		when(userService.createUser(user)).thenReturn(user);

		// Act
		ResponseEntity<User> response = userController.createUser(user);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(user, response.getBody());
		verify(userService).createUser(user);
	}

	/**
	 * Test case for successfully retrieving a user by ID.
	 */
	@Test
	void testGetUserById_Successful() {
		// Arrange
		Long id = 1L;
		User user = new User();
		user.setId(id);
		user.setName("John Doe");

		when(userService.getUserById(id)).thenReturn(Optional.of(user));

		// Act
		ResponseEntity<User> response = userController.getUserById(id);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(user, response.getBody());
		verify(userService).getUserById(id);
	}

	/**
	 * Test case for retrieving a user by ID when the user does not exist.
	 */
	@Test
	void testGetUserById_NotFound() {
		// Arrange
		Long id = 1L;
		when(userService.getUserById(id)).thenReturn(Optional.empty());

		// Act
		ResponseEntity<User> response = userController.getUserById(id);

		// Assert
		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());
		verify(userService).getUserById(id);
	}

	/**
	 * Test case for successfully retrieving a user by email.
	 */
	@Test
	void testGetUserByEmail_Successful() {
		// Arrange
		String email = "test@example.com";
		User user = new User();
		user.setEmail(email);

		when(userService.getUserByEmail(email)).thenReturn(Optional.of(user));

		// Act
		ResponseEntity<User> response = userController.getUserByEmail(email);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(user, response.getBody());
		verify(userService).getUserByEmail(email);
	}

	/**
	 * Test case for retrieving a user by email when the user does not exist.
	 */
	@Test
	void testGetUserByEmail_NotFound() {
		// Arrange
		String email = "test@example.com";
		when(userService.getUserByEmail(email)).thenReturn(Optional.empty());

		// Act
		ResponseEntity<User> response = userController.getUserByEmail(email);

		// Assert
		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());
		verify(userService).getUserByEmail(email);
	}

	/**
	 * Test case for successfully retrieving all users.
	 */
	@Test
	void testGetAllUsers_Successful() {
		// Arrange
		List<User> users = new ArrayList<>();
		users.add(new User());

		when(userService.getAllUsers()).thenReturn(users);

		// Act
		ResponseEntity<List<User>> response = userController.getAllUsers();

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(users, response.getBody());
		verify(userService).getAllUsers();
	}

	/**
	 * Test case for successfully updating a user.
	 */
	@Test
	void testUpdateUser_Successful() {
		// Arrange
		Long id = 1L;
		User updatedUser = new User();
		updatedUser.setId(id);
		updatedUser.setName("Updated Name");

		when(userService.updateUser(id, updatedUser)).thenReturn(Optional.of(updatedUser));

		// Act
		ResponseEntity<User> response = userController.updateUser(id, updatedUser);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(updatedUser, response.getBody());
		verify(userService).updateUser(id, updatedUser);
	}

	/**
	 * Test case for updating a user that does not exist.
	 */
	@Test
	void testUpdateUser_NotFound() {
		// Arrange
		Long id = 1L;
		User updatedUser = new User();
		when(userService.updateUser(id, updatedUser)).thenReturn(Optional.empty());

		// Act
		ResponseEntity<User> response = userController.updateUser(id, updatedUser);

		// Assert
		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());
		verify(userService).updateUser(id, updatedUser);
	}

	/**
	 * Test case for successfully deleting a user.
	 */
	@Test
	void testDeleteUser_Successful() {
		// Arrange
		Long id = 1L;
		doNothing().when(userService).deleteUser(id);

		// Act
		ResponseEntity<Void> response = userController.deleteUser(id);

		// Assert
		assertNotNull(response);
		assertEquals(204, response.getStatusCodeValue());
		verify(userService).deleteUser(id);
	}

	/**
	 * Test case for deleting a user that does not exist.
	 */
	@Test
	void testDeleteUser_NotFound() {
		// Arrange
		Long id = 1L;
		doThrow(new UserNotFoundException("User not found")).when(userService).deleteUser(id);

		// Act
		ResponseEntity<Void> response = userController.deleteUser(id);

		// Assert
		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());
		verify(userService).deleteUser(id);
	}
}
