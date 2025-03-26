package com.fooddelivery.Controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.model.MenuItem;
import com.fooddelivery.service.MenuItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller for managing menu items.
 */
@RestController
@RequestMapping("/api/menu-items")
@Api(value = "Menu Item Management", tags = { "Menu Items" })
@Validated
public class MenuItemController {

	private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);

	@Autowired
	private MenuItemService menuItemService;

	/**
	 * Adds a new menu item.
	 *
	 * @param menuItem the menu item to be added
	 * @return ResponseEntity containing the created MenuItem
	 */
	@PostMapping
	@ApiOperation(value = "Add a new menu item", response = MenuItem.class)
	public ResponseEntity<?> addMenuItem(@Valid @RequestBody MenuItem menuItem) {
		logger.info("Adding menu item: {}", menuItem.getName());

		try {
			MenuItem createdMenuItem = menuItemService.addMenuItem(menuItem);
			return ResponseEntity.ok(createdMenuItem);
		} catch (Exception e) {
			logger.error("Error while adding menu item: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error adding menu item: " + e.getMessage());
		}
	}

	/**
	 * Retrieves menu item details by ID.
	 *
	 * @param id the ID of the menu item
	 * @return ResponseEntity containing the MenuItem if found, or 404 if not found
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Get menu item by ID", response = MenuItem.class)
	public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
		logger.info("Retrieving menu item with ID: {}", id);
		MenuItem menuItem = menuItemService.getMenuItemById(id);
		return ResponseEntity.ok(menuItem);
	}

	/**
	 * Lists all menu items for a specific restaurant.
	 *
	 * @param restaurantId the ID of the restaurant
	 * @return ResponseEntity containing a list of MenuItems
	 */
	@GetMapping("/restaurant/{restaurantId}")
	@ApiOperation(value = "Get menu items by restaurant ID", response = List.class)
	public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurantId(@PathVariable Long restaurantId) {
		logger.info("Retrieving menu items for restaurant ID: {}", restaurantId);
		List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurantId(restaurantId);
		return ResponseEntity.ok(menuItems);
	}

	/**
	 * Updates menu item details.
	 *
	 * @param id              the ID of the menu item to update
	 * @param updatedMenuItem the updated menu item details
	 * @return ResponseEntity containing the updated MenuItem if found, or 404 if
	 *         not found
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Update menu item details", response = MenuItem.class)
	public ResponseEntity<?> updateMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItem updatedMenuItem) {
		logger.info("Updating menu item with ID: {}", id);

		MenuItem updatedItem = menuItemService.updateMenuItem(id, updatedMenuItem);
		return ResponseEntity.ok(updatedItem);

	}

	/**
	 * Deletes a menu item.
	 *
	 * @param id the ID of the menu item to delete
	 * @return ResponseEntity with no content
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a menu item")
	public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
		logger.info("Deleting menu item with ID: {}", id);
		boolean deleted = menuItemService.deleteMenuItem(id);

		if (!deleted) {
			logger.warn("Menu item not found with ID: {}", id);
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

}