package com.govtech.restaurantpicker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.govtech.restaurantpicker.model.RestaurantUser;

@Repository
public interface UserRepository extends JpaRepository<RestaurantUser, Long> {

	Optional<RestaurantUser> findByUsername(String username);

}
