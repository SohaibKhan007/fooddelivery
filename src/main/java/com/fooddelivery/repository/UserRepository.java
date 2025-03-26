package com.fooddelivery.repository;

import com.fooddelivery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities. Provides methods to retrieve
 * users by email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Retrieves a user by their email address.
	 *
	 * @param email the email of the user
	 * @return an Optional containing the user if found, otherwise empty
	 */
	Optional<User> findByEmail(String email);
}
