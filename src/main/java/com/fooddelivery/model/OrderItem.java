package com.fooddelivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Represents an item in an order.
 */
@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Menu item cannot be null")
	@ManyToOne
	private MenuItem menuItem;

	@NotNull(message = "Quantity cannot be null")
	@Positive(message = "Quantity must be a positive value")
	private int quantity;

	// Getters
	public Long getId() {
		return id;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public int getQuantity() {
		return quantity;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public void setQuantity(int quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be a positive value");
		}
		this.quantity = quantity;
	}

}