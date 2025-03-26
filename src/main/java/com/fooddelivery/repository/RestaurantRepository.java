package com.fooddelivery.repository;

import com.fooddelivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Restaurant entities. Provides methods to
 * retrieve restaurants by cuisine type.
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	/**
	 * Retrieves a list of restaurants based on their cuisine type.
	 *
	 * @param cuisine the cuisine type (e.g., "Italian", "Chinese", etc.)
	 * @return a list of restaurants serving the specified cuisine
	 */
	List<Restaurant> findByCuisine(String cuisine);
}
