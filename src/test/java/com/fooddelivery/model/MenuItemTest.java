package com.fooddelivery.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MenuItem}.
 */
class MenuItemTest {

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

        menuItem = new MenuItem(1L, "Burger", "Delicious beef burger", 5.99, restaurant);
    }

    /**
     * Tests the getter methods.
     */
    @Test
    void testGetters() {
        assertEquals(1L, menuItem.getId());
        assertEquals("Burger", menuItem.getName());
        assertEquals("Delicious beef burger", menuItem.getDescription());
        assertEquals(5.99, menuItem.getPrice(), 0.001);
        assertEquals(restaurant, menuItem.getRestaurant());
    }

    /**
     * Tests the setter methods.
     */
    @Test
    void testSetters() {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setId(2L);
        newRestaurant.setName("New Restaurant");

        menuItem.setId(2L);
        menuItem.setName("Pizza");
        menuItem.setDescription("Cheese pizza");
        menuItem.setPrice(8.99);
        menuItem.setRestaurant(newRestaurant);

        assertEquals(2L, menuItem.getId());
        assertEquals("Pizza", menuItem.getName());
        assertEquals("Cheese pizza", menuItem.getDescription());
        assertEquals(8.99, menuItem.getPrice(), 0.001);
        assertEquals(newRestaurant, menuItem.getRestaurant());
    }

    /**
     * Tests the default constructor.
     */
    @Test
    void testNoArgsConstructor() {
        MenuItem newItem = new MenuItem();
        assertNull(newItem.getId());
        assertNull(newItem.getName());
        assertNull(newItem.getDescription());
        assertEquals(0.0, newItem.getPrice(), 0.001);
        assertNull(newItem.getRestaurant());
    }

    /**
     * Tests setting null values.
     */
    @Test
    void testSetNullValues() {
        menuItem.setName(null);
        menuItem.setDescription(null);
        menuItem.setRestaurant(null);

        assertNull(menuItem.getName());
        assertNull(menuItem.getDescription());
        assertNull(menuItem.getRestaurant());
    }

    /**
     * Tests setting boundary values for name and description.
     */
    @Test
    void testBoundaryValues() {
        menuItem.setName("A");
        menuItem.setDescription("A");
        assertEquals("A", menuItem.getName());
        assertEquals("A", menuItem.getDescription());

        String longName = "A".repeat(100);
        String longDescription = "A".repeat(255);
        menuItem.setName(longName);
        menuItem.setDescription(longDescription);
        assertEquals(longName, menuItem.getName());
        assertEquals(longDescription, menuItem.getDescription());
    }

    /**
     * Tests setting a negative price.
     */
    @Test
    void testNegativePrice() {
        // This should be prevented by validation, but we can test the behavior
        menuItem.setPrice(-5.99);
        assertEquals(-5.99, menuItem.getPrice(), 0.001);
    }

    /**
     * Tests setting empty strings for name and description.
     */
    @Test
    void testEmptyStrings() {
        menuItem.setName("");
        menuItem.setDescription("");
        assertEquals("", menuItem.getName());
        assertEquals("", menuItem.getDescription());
    }
}