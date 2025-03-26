package com.fooddelivery.Controller;

import com.fooddelivery.exception.UserNotFoundException;
import com.fooddelivery.model.User;
import com.fooddelivery.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Controller for managing users.
 */
@RestController
@RequestMapping("/api/users")
@Api(value = "User Management", tags = { "Users" })
@Validated
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * Creates a new user.
	 *
	 * @param user the user to be created
	 * @return ResponseEntity containing the created User object
	 */
	@PostMapping
	@ApiOperation(value = "Create a new user", response = User.class)
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		logger.info("Creating user: {}", user.getName());
		User createdUser = userService.createUser(user);
		return ResponseEntity.ok(createdUser);
	}

	/**
	 * Retrieves a user by their unique ID.
	 *
	 * @param id the unique identifier of the user
	 * @return ResponseEntity containing the User if found, or 404 if not found
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Get user by ID", response = User.class)
	public ResponseEntity<User> getUserById(@PathVariable @NotNull Long id) {
		logger.info("Retrieving user with ID: {}", id);
		Optional<User> user = userService.getUserById(id);
		return user.map(ResponseEntity::ok).orElseGet(() -> {
			logger.warn("User with ID {} not found", id);
			return ResponseEntity.notFound().build();
		});
	}

	/**
	 * Retrieves a user by their email.
	 *
	 * @param email the email of the user
	 * @return ResponseEntity containing the User if found, or 404 if not found
	 */
	@GetMapping("/email/{email}")
	@ApiOperation(value = "Get user by email", response = User.class)
	public ResponseEntity<User> getUserByEmail(@PathVariable @Email String email) {
		logger.info("Retrieving user with email: {}", email);
		Optional<User> user = userService.getUserByEmail(email);
		return user.map(ResponseEntity::ok).orElseGet(() -> {
			logger.warn("User with email {} not found", email);
			return ResponseEntity.notFound().build();
		});
	}

	/**
	 * Retrieves all users.
	 *
	 * @return ResponseEntity containing a list of Users
	 */
	@GetMapping
	@ApiOperation(value = "Get all users", response = List.class)
	public ResponseEntity<List<User>> getAllUsers() {
		logger.info("Retrieving all users");
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	/**
	 * Updates user details.
	 *
	 * @param id          the ID of the user to update
	 * @param updatedUser the updated user details
	 * @return ResponseEntity containing the updated User if found, or 404 if not
	 *         found
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Update user details", response = User.class)
	public ResponseEntity<User> updateUser(@PathVariable @NotNull Long id, @Valid @RequestBody User updatedUser) {
		logger.info("Updating user with ID: {}", id);
		Optional<User> user = userService.updateUser(id, updatedUser);
		return user.map(ResponseEntity::ok).orElseGet(() -> {
			logger.warn("User with ID {} not found for update", id);
			return ResponseEntity.notFound().build();
		});
	}

	/**
	 * Deletes a user.
	 *
	 * @param id the ID of the user to delete
	 * @return ResponseEntity with no content
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a user")
	public ResponseEntity<Void> deleteUser(@PathVariable @NotNull Long id) {
		logger.info("Deleting user with ID: {}", id);
		try {
			userService.deleteUser(id); // This method should throw an exception if not found
			return ResponseEntity.noContent().build(); // 204 No Content
		} catch (UserNotFoundException e) {
			logger.warn("User with ID {} not found for deletion", id);
			return ResponseEntity.notFound().build(); // 404 Not Found
		}
	}
}