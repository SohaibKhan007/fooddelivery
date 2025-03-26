package com.fooddelivery.MenuItemDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fooddelivery.model.Restaurant;

/**
 * Unit test for {@link MenuItemDTO}.
 */
class MenuItemDTOTest {

    private MenuItemDTO menuItemDTO;
    private Restaurant restaurant;

    /**
     * Setup method to initialize test data before each test.
     */
    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        menuItemDTO = new MenuItemDTO("Burger", "Delicious beef burger", 5.99, restaurant);
    }

    /**
     * Tests the default constructor.
     */
    @Test
    void testDefaultConstructor() {
        MenuItemDTO emptyItem = new MenuItemDTO();
        assertNotNull(emptyItem);
    }

    /**
     * Tests the parameterized constructor.
     */
    @Test
    void testParameterizedConstructor() {
        assertEquals("Burger", menuItemDTO.getName());
        assertEquals("Delicious beef burger", menuItemDTO.getDescription());
        assertEquals(5.99, menuItemDTO.getPrice());
        assertEquals(restaurant, menuItemDTO.getRestaurant());
    }

    /**
     * Tests the getter and setter for name.
     */
    @Test
    void testSetName() {
        menuItemDTO.setName("Pizza");
        assertEquals("Pizza", menuItemDTO.getName());
    }

    /**
     * Tests the getter and setter for description.
     */
    @Test
    void testSetDescription() {
        menuItemDTO.setDescription("Tasty cheese pizza");
        assertEquals("Tasty cheese pizza", menuItemDTO.getDescription());
    }

    /**
     * Tests the getter and setter for price.
     */
    @Test
    void testSetPrice() {
        menuItemDTO.setPrice(9.99);
        assertEquals(9.99, menuItemDTO.getPrice());
    }

    /**
     * Tests the getter and setter for restaurant.
     */
    @Test
    void testSetRestaurant() {
        Restaurant newRestaurant = new Restaurant();
        menuItemDTO.setRestaurant(newRestaurant);
        assertEquals(newRestaurant, menuItemDTO.getRestaurant());
    }

    /**
     * Tests the builder pattern for MenuItemDTO.
     */
    @Test
    void testBuilder() {
        MenuItemDTO builtItem = new MenuItemDTO.Builder()
                .name("Pasta")
                .description("Creamy Alfredo Pasta")
                .price(12.99)
                .restaurant(restaurant)
                .build();

        assertEquals("Pasta", builtItem.getName());
        assertEquals("Creamy Alfredo Pasta", builtItem.getDescription());
        assertEquals(12.99, builtItem.getPrice());
        assertEquals(restaurant, builtItem.getRestaurant());
    }
}
