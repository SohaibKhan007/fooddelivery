package com.fooddelivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Represents a user in the food delivery system.
 */
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Name cannot be blank")
	@Size(max = 100, message = "Name must be less than 100 characters")
	private String name;

	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "Phone number cannot be blank")
	@Size(max = 15, message = "Phone number must be less than 15 characters")
	private String phoneNumber;

	@NotBlank(message = "Address cannot be blank")
	@Size(max = 255, message = "Address must be less than 255 characters")
	private String address;

	// Getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}