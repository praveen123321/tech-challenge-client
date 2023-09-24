package com.govtech.restaurantpicker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RestaurantChoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long restaurantId;

	private String name;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private RestaurantUser user;

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RestaurantUser getUser() {
		return user;
	}

	public void setUser(RestaurantUser user) {
		this.user = user;
	}

}
