package com.fooddelivery.MenuItemDTO;

import com.fooddelivery.model.Restaurant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO for transferring menu item data.
 */
public class MenuItemDTO {

	@NotNull(message = "Menu item name cannot be null")
	@Size(min = 2, max = 100, message = "Menu item name must be between 2 and 100 characters")
	private String name;

	@NotNull(message = "Description cannot be null")
	@Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
	private String description;

	@NotNull(message = "Price cannot be null")
	@Positive(message = "Price must be greater than zero")
	private double price;

	private Restaurant restaurant;

	// Default constructor
	public MenuItemDTO() {
	}

	// Parameterized constructor
	public MenuItemDTO(String name, String description, double price, Restaurant restaurant) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.restaurant = restaurant;
	}

	// Getters
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

	// Builder Class
	public static class Builder {
		private String name;
		private String description;
		private double price;
		private Restaurant restaurant;

		public Builder() {
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder price(double price) {
			this.price = price;
			return this;
		}

		public Builder restaurant(Restaurant restaurant) {
			this.restaurant = restaurant;
			return this;
		}

		public MenuItemDTO build() {
			return new MenuItemDTO(name, description, price, restaurant);
		}
	}
}
