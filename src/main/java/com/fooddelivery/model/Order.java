package com.fooddelivery.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Represents an order placed by a user.
 */
@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Customer cannot be null")
	@ManyToOne
	private User customer;

	@NotNull(message = "Order items cannot be null")
	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderItem> items;

	@NotNull(message = "Total price cannot be null")
	@Positive(message = "Total price must be a positive value")
	private BigDecimal totalPrice;

	@NotNull(message = "Order status cannot be null")
	private OrderStatus status;

	@NotNull(message = "Order time cannot be null")
	private LocalDateTime orderTime;

	public enum OrderStatus {
		PLACED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
	}

	// Getters
	public Long getId() {
		return id;
	}

	public User getCustomer() {
		return customer;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}
}