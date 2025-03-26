package com.fooddelivery.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.OrderItem;
import com.fooddelivery.model.User;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.UserRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Service layer for managing orders.
 */
@Service
public class OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Places a new order for the specified user.
	 *
	 * @param userId the ID of the user placing the order
	 * @param items  the list of order items
	 * @return the created Order object
	 * @throws ResourceNotFoundException if the user is not found
	 */
	@Transactional
	public Order placeOrder(Long userId, @Valid List<OrderItem> items) {
		User customer = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

		if (items == null || items.isEmpty()) {
			throw new IllegalArgumentException("Missing menu item");
		}

		// Manually validate each OrderItem
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		for (OrderItem item : items) {
			Set<ConstraintViolation<OrderItem>> violations = validator.validate(item);
			if (!violations.isEmpty()) {
				throw new IllegalArgumentException("Invalid OrderItem: " + violations.iterator().next().getMessage());
			}
		}

		Order order = new Order();
		order.setCustomer(customer);
		order.setItems(items);
		order.setStatus(Order.OrderStatus.PLACED);
		order.setOrderTime(LocalDateTime.now());

		// Calculate total price
		BigDecimal total = items.stream().map(item -> {
			if (item.getMenuItem() == null) {
				throw new IllegalArgumentException("Invalid OrderItem: Missing menu item");
			}
			double price = item.getMenuItem().getPrice();
			return BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(item.getQuantity()));
		}).reduce(BigDecimal.ZERO, BigDecimal::add);

		order.setTotalPrice(total);
		logger.info("Order placed successfully for user ID: {} with total price: {}", userId, total);

		return orderRepository.save(order);
	}

	/**
	 * Retrieves orders by their status.
	 *
	 * @param status the status of the orders to retrieve
	 * @return a list of Order objects with the specified status
	 */
	public List<Order> getOrdersByStatus(Order.OrderStatus status) {
		logger.info("Fetching orders with status: {}", status);
		return orderRepository.findByStatus(status);
	}
}