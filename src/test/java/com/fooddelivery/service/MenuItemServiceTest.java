package com.fooddelivery.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.model.MenuItem;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.repository.MenuItemRepository;

/**
 * Unit tests for {@link MenuItemService}.
 */
@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {

	@Mock
	private MenuItemRepository menuItemRepository;

	@InjectMocks
	private MenuItemService menuItemService;

	private MenuItem menuItem;
	private Restaurant restaurant;

	/**
	 * Sets up test data before each test.
	 */
	@BeforeEach
	void setUp() {
		restaurant = new Restaurant();
		restaurant.setId(1L);
		restaurant.setName("Test Restaurant");

		menuItem = new MenuItem();
		menuItem.setId(1L);
		menuItem.setName("Burger");
		menuItem.setDescription("Delicious beef burger");
		menuItem.setPrice(5.99);
		menuItem.setRestaurant(restaurant);
	}

	/**
	 * Tests adding a menu item.
	 */
	@Test
	void testAddMenuItem() {
		when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

		MenuItem createdMenuItem = menuItemService.addMenuItem(menuItem);

		assertNotNull(createdMenuItem);
		assertEquals("Burger", createdMenuItem.getName());
		verify(menuItemRepository, times(1)).save(menuItem);
	}

	/**
	 * Tests retrieving a menu item by ID successfully.
	 */
	@Test
	void testGetMenuItemById_Success() {
		when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

		MenuItem foundMenuItem = menuItemService.getMenuItemById(1L);

		assertNotNull(foundMenuItem);
		assertEquals("Burger", foundMenuItem.getName());
		verify(menuItemRepository, times(1)).findById(1L);
	}

	/**
	 * Tests retrieving a menu item by ID when not found.
	 */
	@Test
	void testGetMenuItemById_NotFound() {
		when(menuItemRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> menuItemService.getMenuItemById(1L));

		verify(menuItemRepository, times(1)).findById(1L);
	}

	/**
	 * Tests retrieving all menu items for a specific restaurant.
	 */
	@Test
	void testGetMenuItemsByRestaurantId() {
		when(menuItemRepository.findByRestaurantId(1L)).thenReturn(Arrays.asList(menuItem));

		List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurantId(1L);

		assertFalse(menuItems.isEmpty());
		assertEquals(1, menuItems.size());
		verify(menuItemRepository, times(1)).findByRestaurantId(1L);
	}

	/**
	 * Tests updating a menu item successfully.
	 */
	@Test
	void testUpdateMenuItem_Success() {
		MenuItem updatedMenuItem = new MenuItem();
		updatedMenuItem.setName("Updated Burger");
		updatedMenuItem.setDescription("Tasty updated burger");
		updatedMenuItem.setPrice(6.99);
		updatedMenuItem.setRestaurant(restaurant);

		when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
		when(menuItemRepository.save(any(MenuItem.class))).thenReturn(updatedMenuItem);

		MenuItem result = menuItemService.updateMenuItem(1L, updatedMenuItem);

		assertNotNull(result);
		assertEquals("Updated Burger", result.getName());
		assertEquals(Double.valueOf(6.99), result.getPrice());
		verify(menuItemRepository, times(1)).findById(1L);
		verify(menuItemRepository, times(1)).save(menuItem);
	}

	/**
	 * Tests updating a menu item when not found.
	 */
	@Test
	void testUpdateMenuItem_NotFound() {
		when(menuItemRepository.findById(1L)).thenReturn(Optional.empty());

		MenuItem updatedMenuItem = new MenuItem();
		updatedMenuItem.setName("Updated Burger");

		assertThrows(ResourceNotFoundException.class, () -> menuItemService.updateMenuItem(1L, updatedMenuItem));

		verify(menuItemRepository, times(1)).findById(1L);
		verify(menuItemRepository, never()).save(any(MenuItem.class));
	}

	/**
	 * Tests deleting a menu item successfully.
	 */
	@Test
	void testDeleteMenuItem_Success() {
		when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

		boolean result = menuItemService.deleteMenuItem(1L);

		assertTrue(result);
		verify(menuItemRepository, times(1)).findById(1L);
		verify(menuItemRepository, times(1)).delete(menuItem);
	}

	/**
	 * Tests deleting a menu item when not found.
	 */
	@Test
	void testDeleteMenuItem_NotFound() {
		when(menuItemRepository.findById(1L)).thenReturn(Optional.empty());

		boolean result = menuItemService.deleteMenuItem(1L);

		assertFalse(result);
		verify(menuItemRepository, times(1)).findById(1L);
		verify(menuItemRepository, never()).delete(any(MenuItem.class));
	}
}
