package com.fooddelivery.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Order}.
 */
class OrderTest {

	private Order order;
	private User customer;
	private List<OrderItem> orderItems;
	private BigDecimal totalPrice;
	private LocalDateTime orderTime;

	/**
	 * Sets up test data before each test.
	 */
	@BeforeEach
	void setUp() {
		customer = new User();
		customer.setId(1L);
		customer.setName("John Doe");

		orderItems = new ArrayList<>();
		orderItems.add(new OrderItem());

		totalPrice = BigDecimal.valueOf(29.99);
		orderTime = LocalDateTime.now();

		order = new Order();
		order.setId(1L);
		order.setCustomer(customer);
		order.setItems(orderItems);
		order.setTotalPrice(totalPrice);
		order.setStatus(Order.OrderStatus.PLACED);
		order.setOrderTime(orderTime);
	}

	/**
	 * Tests the getter methods.
	 */
	@Test
	void testGetters() {
		assertEquals(1L, order.getId());
		assertEquals(customer, order.getCustomer());
		assertEquals(orderItems, order.getItems());
		assertEquals(totalPrice, order.getTotalPrice());
		assertEquals(Order.OrderStatus.PLACED, order.getStatus());
		assertEquals(orderTime, order.getOrderTime());
	}

	/**
	 * Tests the setter methods.
	 */
	@Test
	void testSetters() {
		User newCustomer = new User();
		newCustomer.setId(2L);
		newCustomer.setName("Jane Doe");

		List<OrderItem> newOrderItems = new ArrayList<>();
		newOrderItems.add(new OrderItem());

		BigDecimal newTotalPrice = BigDecimal.valueOf(49.99);
		LocalDateTime newOrderTime = LocalDateTime.now().plusHours(1);

		order.setId(2L);
		order.setCustomer(newCustomer);
		order.setItems(newOrderItems);
		order.setTotalPrice(newTotalPrice);
		order.setStatus(Order.OrderStatus.DELIVERED);
		order.setOrderTime(newOrderTime);

		assertEquals(2L, order.getId());
		assertEquals(newCustomer, order.getCustomer());
		assertEquals(newOrderItems, order.getItems());
		assertEquals(newTotalPrice, order.getTotalPrice());
		assertEquals(Order.OrderStatus.DELIVERED, order.getStatus());
		assertEquals(newOrderTime, order.getOrderTime());
	}

	/**
	 * Tests the default constructor.
	 */
	@Test
	void testNoArgsConstructor() {
		Order newOrder = new Order();
		assertNull(newOrder.getId());
		assertNull(newOrder.getCustomer());
		assertNull(newOrder.getItems());
		assertNull(newOrder.getTotalPrice());
		assertNull(newOrder.getStatus());
		assertNull(newOrder.getOrderTime());
	}

	/**
	 * Tests enum values of OrderStatus.
	 */
	@Test
	void testOrderStatusEnum() {
		assertEquals(Order.OrderStatus.PLACED, Order.OrderStatus.valueOf("PLACED"));
		assertEquals(Order.OrderStatus.PREPARING, Order.OrderStatus.valueOf("PREPARING"));
		assertEquals(Order.OrderStatus.OUT_FOR_DELIVERY, Order.OrderStatus.valueOf("OUT_FOR_DELIVERY"));
		assertEquals(Order.OrderStatus.DELIVERED, Order.OrderStatus.valueOf("DELIVERED"));
		assertEquals(Order.OrderStatus.CANCELLED, Order.OrderStatus.valueOf("CANCELLED"));
	}
}
