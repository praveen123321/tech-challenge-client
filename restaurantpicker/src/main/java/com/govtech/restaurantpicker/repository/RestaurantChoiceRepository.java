package com.govtech.restaurantpicker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.govtech.restaurantpicker.model.RestaurantChoice;

@Repository
public interface RestaurantChoiceRepository extends JpaRepository<RestaurantChoice, Long> {

	@Query("SELECT rc FROM RestaurantChoice rc WHERE rc.user.userId = :userId")
	List<RestaurantChoice> findAllByUserId(Long userId);
}
