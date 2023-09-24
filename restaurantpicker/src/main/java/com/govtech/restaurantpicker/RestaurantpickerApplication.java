package com.govtech.restaurantpicker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication
public class RestaurantpickerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantpickerApplication.class, args);
	}

}
