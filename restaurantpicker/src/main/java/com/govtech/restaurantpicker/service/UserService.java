package com.govtech.restaurantpicker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.govtech.restaurantpicker.model.RestaurantUser;
import com.govtech.restaurantpicker.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public RestaurantUser saveUser(RestaurantUser user) {
		return userRepository.save(user);
	}

	public RestaurantUser getUserByUsername(String userName) {
		Optional<RestaurantUser> user = userRepository.findByUsername(userName);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}

	public List<RestaurantUser> getAllUsers() {
		return userRepository.findAll();
	}

}
