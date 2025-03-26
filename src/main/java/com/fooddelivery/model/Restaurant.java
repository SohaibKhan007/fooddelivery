package com.fooddelivery.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Represents a restaurant in the food delivery system.
 */
@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Name cannot be blank")
	@Size(max = 100, message = "Name must be less than 100 characters")
	private String name;

	@NotBlank(message = "Address cannot be blank")
	@Size(max = 255, message = "Address must be less than 255 characters")
	private String address;

	@NotBlank(message = "Cuisine cannot be blank")
	@Size(max = 50, message = "Cuisine must be less than 50 characters")
	private String cuisine;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	private List<MenuItem> menuItems;

	// Getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getCuisine() {
		return cuisine;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
}