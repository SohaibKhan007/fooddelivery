package com.fooddelivery.service;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.model.MenuItem;
import com.fooddelivery.repository.MenuItemRepository;

/**
 * Service layer for managing menu items.
 */
@Service
@Validated
public class MenuItemService {

	private final MenuItemRepository menuItemRepository;
	private static final Logger log = LoggerFactory.getLogger(MenuItemService.class);

	public MenuItemService(MenuItemRepository menuItemRepository) {
		this.menuItemRepository = menuItemRepository;
	}

	/**
	 * Adds a new menu item.
	 *
	 * @param menuItem the menu item to be added
	 * @return the created MenuItem
	 */
	@Transactional
	public MenuItem addMenuItem(@Valid MenuItem menuItem) {
		log.info("Adding a new menu item: {}", menuItem.getName());
		return menuItemRepository.save(menuItem);
	}

	/**
	 * Retrieves a menu item by its ID.
	 *
	 * @param id the ID of the menu item
	 * @return the MenuItem if found
	 * @throws ResourceNotFoundException if the menu item is not found
	 */
	public MenuItem getMenuItemById(Long id) {
		log.info("Fetching menu item with ID: {}", id);
		return menuItemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item not found with ID: " + id));
	}

	/**
	 * Retrieves all menu items for a specific restaurant.
	 *
	 * @param restaurantId the ID of the restaurant
	 * @return a list of MenuItems
	 */
	public List<MenuItem> getMenuItemsByRestaurantId(Long restaurantId) {
		log.info("Fetching menu items for restaurant ID: {}", restaurantId);
		return menuItemRepository.findByRestaurantId(restaurantId);
	}

	/**
	 * Updates a menu item.
	 *
	 * @param id              the ID of the menu item to update
	 * @param updatedMenuItem the updated menu item details
	 * @return the updated MenuItem
	 * @throws ResourceNotFoundException if the menu item is not found
	 */
	@Transactional
	public MenuItem updateMenuItem(Long id, @Valid MenuItem updatedMenuItem) {
		return menuItemRepository.findById(id).map(menuItem -> {
			menuItem.setName(updatedMenuItem.getName());
			menuItem.setDescription(updatedMenuItem.getDescription());
			menuItem.setPrice(updatedMenuItem.getPrice());
			return menuItemRepository.save(menuItem);
		}).orElseThrow(() -> new ResourceNotFoundException("Menu item not found with ID: " + id));
	}

	/**
	 * Deletes a menu item by ID.
	 *
	 * @param id the ID of the menu item to delete
	 * @throws ResourceNotFoundException if the menu item is not found
	 */
	@Transactional
	public boolean deleteMenuItem(Long id) {
		log.info("Deleting menu item with ID: {}", id);
		return menuItemRepository.findById(id).map(menuItem -> {
			menuItemRepository.delete(menuItem);
			return true;
		}).orElse(false);
	}

}