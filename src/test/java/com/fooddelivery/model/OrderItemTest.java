package com.fooddelivery.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link OrderItem}.
 */
class OrderItemTest {

	private OrderItem orderItem;
	private MenuItem menuItem;

	/**
	 * Sets up test data before each test.
	 */
	@BeforeEach
	void setUp() {
		menuItem = new MenuItem();
		menuItem.setId(1L);
		menuItem.setName("Burger");
		menuItem.setPrice(5.99);

		orderItem = new OrderItem();
		orderItem.setId(1L);
		orderItem.setMenuItem(menuItem);
		orderItem.setQuantity(2);
	}

	/**
	 * Tests the getter methods.
	 */
	@Test
	void testGetters() {
		assertEquals(1L, orderItem.getId());
		assertEquals(menuItem, orderItem.getMenuItem());
		assertEquals(2, orderItem.getQuantity());
	}

	/**
	 * Tests the setter methods.
	 */
	@Test
	void testSetters() {
		MenuItem newMenuItem = new MenuItem();
		newMenuItem.setId(2L);
		newMenuItem.setName("Pizza");
		newMenuItem.setPrice(8.99);

		orderItem.setId(2L);
		orderItem.setMenuItem(newMenuItem);
		orderItem.setQuantity(3);

		assertEquals(2L, orderItem.getId());
		assertEquals(newMenuItem, orderItem.getMenuItem());
		assertEquals(3, orderItem.getQuantity());
	}

	/**
	 * Tests the default constructor.
	 */
	@Test
	void testNoArgsConstructor() {
		OrderItem newOrderItem = new OrderItem();
		assertNull(newOrderItem.getId());
		assertNull(newOrderItem.getMenuItem());
		assertEquals(0, newOrderItem.getQuantity());
	}

	/**
	 * Tests validation for negative quantity.
	 */
	@Test
	void testNegativeQuantity() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			orderItem.setQuantity(-1);
		});

		assertTrue(thrown.getMessage().contains("Quantity must be a positive value"));
	}
}
