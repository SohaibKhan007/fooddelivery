package com.fooddelivery.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import com.fooddelivery.model.Order;
import com.fooddelivery.model.OrderItem;
import com.fooddelivery.model.User;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.UserRepository;

/**
 * Unit tests for {@link OrderService}.
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private OrderService orderService;

	private User user;
	private Order order;
	private OrderItem orderItem;
	private MenuItem menuItem;

	/**
	 * Sets up test data before each test.
	 */
	@BeforeEach
	void setUp() {
		user = new User();
		user.setId(1L);
		user.setName("John Doe");

		menuItem = new MenuItem();
		menuItem.setId(1L);
		menuItem.setName("Pizza");
		menuItem.setPrice(Double.valueOf(9.99));

		orderItem = new OrderItem();
		orderItem.setId(1L);
		orderItem.setMenuItem(menuItem);
		orderItem.setQuantity(2);

		order = new Order();
		order.setId(1L);
		order.setCustomer(user);
		order.setItems(List.of(orderItem));
		order.setTotalPrice(BigDecimal.valueOf(19.98));
		order.setStatus(Order.OrderStatus.PLACED);
		order.setOrderTime(LocalDateTime.now());
	}

	/**
	 * Tests placing an order successfully.
	 */
	@Test
	void testPlaceOrder_Success() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(orderRepository.save(any(Order.class))).thenReturn(order);

		Order placedOrder = orderService.placeOrder(1L, List.of(orderItem));

		assertNotNull(placedOrder);
		assertEquals(1L, placedOrder.getCustomer().getId());
		assertEquals(BigDecimal.valueOf(19.98), placedOrder.getTotalPrice());
		verify(userRepository, times(1)).findById(1L);
		verify(orderRepository, times(1)).save(any(Order.class));
	}

	/**
	 * Tests placing an order when the user is not found.
	 */
	@Test
	void testPlaceOrder_UserNotFound() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> orderService.placeOrder(1L, List.of(orderItem)));

		assertEquals("User not found with ID: 1", exception.getMessage());
		verify(userRepository, times(1)).findById(1L);
		verify(orderRepository, never()).save(any(Order.class));
	}

	/**
	 * Tests placing an order with an invalid order item (missing menu item).
	 */
	@Test
	void testPlaceOrder_InvalidOrderItem() {
		OrderItem invalidItem = new OrderItem();
		invalidItem.setId(2L);
		invalidItem.setQuantity(2);

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> orderService.placeOrder(1L, List.of(invalidItem)));

		assertEquals("Invalid OrderItem: Menu item cannot be null", exception.getMessage());
		verify(userRepository, times(1)).findById(1L);
		verify(orderRepository, never()).save(any(Order.class));
	}

	/**
	 * Tests retrieving orders by status.
	 */
	@Test
	void testGetOrdersByStatus_Success() {
		when(orderRepository.findByStatus(Order.OrderStatus.PLACED)).thenReturn(Arrays.asList(order));

		List<Order> orders = orderService.getOrdersByStatus(Order.OrderStatus.PLACED);

		assertFalse(orders.isEmpty());
		assertEquals(1, orders.size());
		assertEquals(Order.OrderStatus.PLACED, orders.get(0).getStatus());
		verify(orderRepository, times(1)).findByStatus(Order.OrderStatus.PLACED);
	}

	/**
	 * Tests retrieving orders when there are no orders with the specified status.
	 */
	@Test
	void testGetOrdersByStatus_NoOrdersFound() {
		when(orderRepository.findByStatus(Order.OrderStatus.DELIVERED)).thenReturn(List.of());

		List<Order> orders = orderService.getOrdersByStatus(Order.OrderStatus.DELIVERED);

		assertTrue(orders.isEmpty());
		verify(orderRepository, times(1)).findByStatus(Order.OrderStatus.DELIVERED);
	}
}
