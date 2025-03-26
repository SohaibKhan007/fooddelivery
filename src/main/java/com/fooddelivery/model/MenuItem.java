package com.fooddelivery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Represents a menu item in a restaurant.
 */
@Entity
public class MenuItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Name cannot be null")
	@Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
	private String name;

	@Size(max = 255, message = "Description must be less than 255 characters")
	private String description;

	@NotNull(message = "Price cannot be null")
	@Positive(message = "Price must be a positive value")
	private double price;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	// No-args constructor
	public MenuItem() {
	}

	// All-args constructor
	public MenuItem(Long id, String name, String description, double price, Restaurant restaurant) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.restaurant = restaurant;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
}