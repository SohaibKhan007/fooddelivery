package com.fooddelivery.repository;

import com.fooddelivery.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing MenuItem entities.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    /**
     * Finds all menu items associated with a specific restaurant ID.
     *
     * @param restaurantId the ID of the restaurant
     * @return a list of MenuItem objects associated with the given restaurant ID
     */
    List<MenuItem> findByRestaurantId(Long restaurantId);
}