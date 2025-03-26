package com.fooddelivery.repository;

import com.fooddelivery.model.Order;
import com.fooddelivery.model.Order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Order entities. Provides methods to
 * retrieve orders by customer ID and status.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	/**
	 * Retrieves a list of orders for a specific customer.
	 *
	 * @param customerId the unique ID of the customer
	 * @return a list of orders associated with the given customer ID
	 */
	List<Order> findByCustomerId(Long customerId);

	/**
	 * Retrieves a list of orders based on their status.
	 *
	 * @param status the current status of the orders (e.g., PENDING, COMPLETED)
	 * @return a list of orders matching the given status
	 */
	List<Order> findByStatus(OrderStatus status);
}
