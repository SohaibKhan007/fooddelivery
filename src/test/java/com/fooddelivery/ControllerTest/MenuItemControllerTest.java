package com.fooddelivery.ControllerTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fooddelivery.Controller.MenuItemController;
import com.fooddelivery.model.MenuItem;
import com.fooddelivery.service.MenuItemService;

/**
 * Test class for MenuItemController. This class contains unit tests for the
 * MenuItemController using MockMvc.
 */
@WebMvcTest(MenuItemController.class)
public class MenuItemControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private MenuItemController menuItemController; // ✅ Inject into controller

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // ✅ Initialize mocks
	}

	@MockBean
	private MenuItemService menuItemService;

	/**
	 * Sets up the test environment before each test.
	 */
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Tests the addMenuItem endpoint. Verifies that a new menu item can be added
	 * successfully.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void testAddMenuItem() throws Exception {
		// Arrange: Create a valid MenuItem object
		MenuItem menuItem = new MenuItem();
		menuItem.setName("Pizza");
		menuItem.setPrice(9.99);

		when(menuItemService.addMenuItem(any(MenuItem.class))).thenReturn(menuItem);

		// Act & Assert: Perform POST request and check response
		mockMvc.perform(post("/api/menu-items")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Pizza\", \"price\": 9.99}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Pizza"))
				.andExpect(jsonPath("$.price").value(9.99));
	}

	/**
	 * Tests the getMenuItemById endpoint. Verifies that a menu item can be
	 * retrieved by its ID.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void testGetMenuItemById() throws Exception {
		MenuItem menuItem = new MenuItem();
		menuItem.setId(1L);
		menuItem.setName("Pizza");

		when(menuItemService.getMenuItemById(1L)).thenReturn(menuItem);

		mockMvc.perform(get("/api/menu-items/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Pizza"));
	}

	/**
	 * Tests the getMenuItemsByRestaurantId endpoint. Verifies that menu items can
	 * be retrieved by restaurant ID.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void testGetMenuItemsByRestaurantId() throws Exception {
		MenuItem menuItem = new MenuItem();
		menuItem.setName("Pizza");

		when(menuItemService.getMenuItemsByRestaurantId(1L)).thenReturn(Collections.singletonList(menuItem));

		mockMvc.perform(get("/api/menu-items/restaurant/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Pizza"));
	}

	/**
	 * Tests the updateMenuItem endpoint. Verifies that a menu item can be updated
	 * successfully.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void testUpdateMenuItem() throws Exception {
		// Arrange
		MenuItem menuItem = new MenuItem();
		menuItem.setId(1L);
		menuItem.setName("Pizza");
		menuItem.setPrice(12.99);
		menuItem.setDescription("Delicious Cheese Pizza");

		when(menuItemService.updateMenuItem(eq(1L), any(MenuItem.class))).thenReturn(menuItem);

		// Act & Assert
		mockMvc.perform(put("/api/menu-items/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Pizza\", \"price\":12.99, \"description\":\"Delicious Cheese Pizza\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Pizza"))
				.andExpect(jsonPath("$.price").value(12.99))
				.andExpect(jsonPath("$.description").value("Delicious Cheese Pizza"));
	}

	/**
	 * Tests the deleteMenuItem endpoint. Verifies that a menu item can be deleted
	 * successfully.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	void testDeleteMenuItem_Successful() {
		// Arrange
		Long id = 1L;
		when(menuItemService.deleteMenuItem(id)).thenReturn(true);

		// Act
		ResponseEntity<Void> response = menuItemController.deleteMenuItem(id);

		// Assert
		assertNotNull(response);
		assertEquals(204, response.getStatusCodeValue());
		verify(menuItemService).deleteMenuItem(id);
	}
}