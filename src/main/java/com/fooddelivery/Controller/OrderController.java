package com.fooddelivery.Controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.model.Order;
import com.fooddelivery.model.OrderItem;
import com.fooddelivery.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller for managing orders.
 */
@RestController
@RequestMapping("/api/orders")
@Api(value = "Order Management", tags = { "Orders" })
@Validated
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	/**
	 * Places a new order for the specified user.
	 *
	 * @param userId the ID of the user placing the order
	 * @param items  the list of order items
	 * @return ResponseEntity containing the created Order object
	 */
	@PostMapping("/place")
	@ApiOperation(value = "Place a new order", response = Order.class)
	public ResponseEntity<?> placeOrder(@NotNull @RequestParam Long userId, @Valid @RequestBody List<OrderItem> items) {
		logger.info("Placing order for user ID: {}", userId);
		try {
			if (items == null || items.isEmpty()) {
				logger.warn("Order items cannot be empty");
				return ResponseEntity.badRequest().body("Order items cannot be empty");
			}

			Order order = orderService.placeOrder(userId, items);
			return ResponseEntity.ok(order);

		} catch (IllegalArgumentException e) { // Handle validation errors explicitly
			logger.warn("Invalid input: {}", e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage()); // Returns 400 for bad input
		} catch (Exception e) {
			logger.error("Error placing order for user ID {}: {}", userId, e.getMessage(), e);
			return ResponseEntity.status(500).body("Error placing order: " + e.getMessage());
		}
	}

	/**
	 * Retrieves orders by their status.
	 *
	 * @param status the status of the orders to retrieve
	 * @return ResponseEntity containing a list of orders
	 */
	@GetMapping("/status/{status}")
	@ApiOperation(value = "Get orders by status", response = List.class)
	public ResponseEntity<?> getOrdersByStatus(@PathVariable Order.OrderStatus status) {
		logger.info("Retrieving orders with status: {}", status);
		try {
			if (status == null) {
				logger.warn("Order status cannot be null");
				return ResponseEntity.badRequest().body("Order status cannot be null");
			}

			List<Order> orders = orderService.getOrdersByStatus(status);
			return ResponseEntity.ok(orders);
		} catch (Exception e) {
			logger.error("Error retrieving orders: {}", e.getMessage());
			return ResponseEntity.internalServerError().body("Error retrieving orders: " + e.getMessage());
		}
	}
}