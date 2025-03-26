package com.fooddelivery.service;

import com.fooddelivery.exception.UserNotFoundException;
import com.fooddelivery.model.User;
import com.fooddelivery.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing users.
 */
@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	/**
	 * Creates a new user.
	 *
	 * @param user the user to be created
	 * @return the created User object
	 */
	public User createUser(@Valid User user) {
		logger.info("Creating user: {}", user.getName());
		return userRepository.save(user);
	}

	/**
	 * Retrieves a user by their unique ID.
	 *
	 * @param id the unique identifier of the user
	 * @return the User if found
	 * @throws UserNotFoundException if no user is found with the given ID
	 */
	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	/**
	 * Retrieves a user by their email.
	 *
	 * @param email the email of the user
	 * @return the User if found
	 */
	public Optional<User> getUserByEmail(String email) {
		logger.info("Retrieving user with email: {}", email);
		return userRepository.findByEmail(email);
	}

	/**
	 * Retrieves all users.
	 *
	 * @return a list of all Users
	 */
	public List<User> getAllUsers() {
		logger.info("Retrieving all users");
		return userRepository.findAll();
	}

	/**
	 * Updates user details.
	 *
	 * @param id          the ID of the user to update
	 * @param updatedUser the updated user details
	 * @return the updated User object
	 * @throws UserNotFoundException if no user is found with the given ID
	 */
	@Transactional
	public Optional<User> updateUser(Long id, User updatedUser) {
		return userRepository.findById(id).map(existingUser -> {
			existingUser.setName(updatedUser.getName());
			existingUser.setEmail(updatedUser.getEmail());
			existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
			existingUser.setAddress(updatedUser.getAddress());
			return userRepository.save(existingUser);
		});
	}

	/**
	 * Deletes a user by ID.
	 *
	 * @param id the ID of the user to delete
	 * @throws UserNotFoundException if no user is found with the given ID
	 */
	public void deleteUser(Long id) {
		logger.info("Deleting user with ID: {}", id);
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException("User not found with ID: " + id);
		}
		userRepository.deleteById(id);
	}
}