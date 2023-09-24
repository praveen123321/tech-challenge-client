package com.govtech.restaurantpicker.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class RestaurantUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(unique = true)
	private String username;
	private String password;

	@OneToMany(mappedBy = "user")
	private List<RestaurantChoice> restaurants;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * public List<RestaurantChoice> getRestaurants() { return restaurants; }
	 * 
	 * public void setRestaurants(List<RestaurantChoice> restaurants) {
	 * this.restaurants = restaurants; }
	 */

}
