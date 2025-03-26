package com.fooddelivery.ControllerTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.fooddelivery.Controller.OrderController;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.OrderItem;
import com.fooddelivery.service.OrderService;

/**
 * Unit tests for {@link OrderController}.
 */
class OrderControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(OrderControllerTest.class);

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test case for successfully placing an order.
	 */
	@Test
	void testPlaceOrder_Successful() {
		// Arrange
		Long userId = 1L;
		List<OrderItem> items = new ArrayList<>();
		items.add(new OrderItem()); // ✅ Ensure at least one valid item is present

		Order order = new Order();
		order.setId(1L);

		when(orderService.placeOrder(userId, items)).thenReturn(order);

		// Act
		ResponseEntity<?> response = orderController.placeOrder(userId, items);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue()); // ✅ Now correctly expects 200
		assertEquals(order, response.getBody());
		verify(orderService).placeOrder(userId, items);
	}

	/**
	 * Test case for attempting to place an order with an empty list of items.
	 */
	@Test
	void testPlaceOrder_EmptyItems() {
		// Arrange
		Long userId = 1L;
		List<OrderItem> emptyItems = new ArrayList<>();

		// Act
		ResponseEntity<?> response = orderController.placeOrder(userId, emptyItems);

		// Assert
		assertNotNull(response);
		assertEquals(400, response.getStatusCodeValue());
		assertEquals("Order items cannot be empty", response.getBody()); // ✅ Fix: Now matches controller output
	}

	/**
	 * Test case for successfully retrieving orders by status.
	 */
	@Test
	void testGetOrdersByStatus_Successful() {
		// Arrange
		Order.OrderStatus status = Order.OrderStatus.PLACED;
		List<Order> expectedOrders = new ArrayList<>();
		Order order = new Order();
		order.setStatus(status);
		expectedOrders.add(order);

		when(orderService.getOrdersByStatus(status)).thenReturn(expectedOrders);

		// Act
		ResponseEntity<?> response = orderController.getOrdersByStatus(status);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(expectedOrders, response.getBody());
		verify(orderService).getOrdersByStatus(status);
	}

	/**
	 * Test case for attempting to retrieve orders with a null status.
	 */
	@Test
	void testGetOrdersByStatus_NullStatus() {
		// Act
		ResponseEntity<?> response = orderController.getOrdersByStatus(null);

		// Assert
		assertNotNull(response);
		assertEquals(400, response.getStatusCodeValue());
		assertEquals("Order status cannot be null", response.getBody());
	}

	/**
	 * Test case for handling an exception during order placement.
	 */
	@Test
	void testPlaceOrder_Exception() {
		// Arrange
		Long userId = 1L;
		List<OrderItem> items = new ArrayList<>();
		items.add(new OrderItem()); // ✅ Ensure valid data to bypass validation failure

		when(orderService.placeOrder(userId, items)).thenThrow(new RuntimeException("Service error"));

		// Act
		ResponseEntity<?> response = orderController.placeOrder(userId, items);

		// Assert
		assertNotNull(response);
		assertEquals(500, response.getStatusCodeValue()); // ✅ Now correctly expects 500
		assertTrue(response.getBody().toString().contains("Error placing order: Service error"));
	}

	/**
	 * Test case for handling an exception while retrieving orders by status.
	 */
	@Test
	void testGetOrdersByStatus_Exception() {
		// Arrange
		Order.OrderStatus status = Order.OrderStatus.PLACED;

		when(orderService.getOrdersByStatus(status)).thenThrow(new RuntimeException("Service error"));

		// Act
		ResponseEntity<?> response = orderController.getOrdersByStatus(status);

		// Assert
		assertNotNull(response);
		assertEquals(500, response.getStatusCodeValue());
		assertTrue(response.getBody().toString().contains("Error retrieving orders: Service error"));
	}
}
