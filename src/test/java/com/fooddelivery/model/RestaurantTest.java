package com.fooddelivery.model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Restaurant}.
 */
class RestaurantTest {

	private Restaurant restaurant;
	private List<MenuItem> menuItems;

	/**
	 * Sets up test data before each test.
	 */
	@BeforeEach
	void setUp() {
		menuItems = new ArrayList<>();
		MenuItem menuItem = new MenuItem();
		menuItem.setId(1L);
		menuItem.setName("Burger");
		menuItem.setPrice(5.99);
		menuItems.add(menuItem);

		restaurant = new Restaurant();
		restaurant.setId(1L);
		restaurant.setName("Test Restaurant");
		restaurant.setAddress("123 Main Street");
		restaurant.setCuisine("Italian");
		restaurant.setMenuItems(menuItems);
	}

	/**
	 * Tests the getter methods.
	 */
	@Test
	void testGetters() {
		assertEquals(1L, restaurant.getId());
		assertEquals("Test Restaurant", restaurant.getName());
		assertEquals("123 Main Street", restaurant.getAddress());
		assertEquals("Italian", restaurant.getCuisine());
		assertEquals(menuItems, restaurant.getMenuItems());
	}

	/**
	 * Tests the setter methods.
	 */
	@Test
	void testSetters() {
		List<MenuItem> newMenuItems = new ArrayList<>();
		MenuItem newMenuItem = new MenuItem();
		newMenuItem.setId(2L);
		newMenuItem.setName("Pizza");
		newMenuItem.setPrice(8.99);
		newMenuItems.add(newMenuItem);

		restaurant.setId(2L);
		restaurant.setName("New Restaurant");
		restaurant.setAddress("456 Another Street");
		restaurant.setCuisine("Mexican");
		restaurant.setMenuItems(newMenuItems);

		assertEquals(2L, restaurant.getId());
		assertEquals("New Restaurant", restaurant.getName());
		assertEquals("456 Another Street", restaurant.getAddress());
		assertEquals("Mexican", restaurant.getCuisine());
		assertEquals(newMenuItems, restaurant.getMenuItems());
	}

	/**
	 * Tests the default constructor.
	 */
	@Test
	void testNoArgsConstructor() {
		Restaurant newRestaurant = new Restaurant();
		assertNull(newRestaurant.getId());
		assertNull(newRestaurant.getName());
		assertNull(newRestaurant.getAddress());
		assertNull(newRestaurant.getCuisine());
		assertNull(newRestaurant.getMenuItems());
	}
}
