package com.govtech.restaurantpicker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.govtech.restaurantpicker.model.RestaurantChoice;
import com.govtech.restaurantpicker.model.vo.ErrorResponseVO;
import com.govtech.restaurantpicker.model.vo.RestaurantChoiceVO;
import com.govtech.restaurantpicker.service.AuthenticationService;
import com.govtech.restaurantpicker.service.RestaurantChoiceService;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private RestaurantChoiceService restaurantChoiceService;

	@CrossOrigin
	@PostMapping("/submit-choices")
	public ResponseEntity<?> submitChoice(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
			@RequestBody RestaurantChoice choice) {

		if (authenticationService.isEmpty(authorization)) {
			return authenticationService.getErrorResponseEntity("Unauthorized Request", "Invalid token",
					HttpStatus.UNAUTHORIZED);
		}

		String token = authenticationService.extractToken(authorization);

		if (authenticationService.isEmpty(token)) {
			return authenticationService.getErrorResponseEntity("Unauthorized Request", "Invalid token",
					HttpStatus.UNAUTHORIZED);
		}

		boolean isTokenValid = false;

		try {
			isTokenValid = authenticationService.validateToken(token, choice.getUser().getUsername());

		} catch (Exception e) {
			new ResponseEntity<>(new ErrorResponseVO("Unauthorized Request", "Invalid token"), HttpStatus.UNAUTHORIZED);
		}

		if (isTokenValid) {
			RestaurantChoiceVO restaurantChoiceVO = null;
			try {
				restaurantChoiceVO = restaurantChoiceService.saveChoice(choice);
			} catch (Exception e) {
				e.printStackTrace();
				return authenticationService.getErrorResponseEntity("Database Error",
						"Could not save the added restaurant", HttpStatus.BAD_REQUEST);

			}
			return new ResponseEntity<>(restaurantChoiceVO, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(new ErrorResponseVO("Unauthorized Request", "Invalid token"),
					HttpStatus.UNAUTHORIZED);
		}

	}

	@CrossOrigin
	@GetMapping("/restaurantList/{username}")
	public ResponseEntity<?> getRestaurantsForTeam(@PathVariable("username") String username,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

		if (authenticationService.isEmpty(authorization)) {
			return authenticationService.getErrorResponseEntity("Unauthorized Request", "Invalid token",
					HttpStatus.UNAUTHORIZED);
		}
		String token = authenticationService.extractToken(authorization);

		if (authenticationService.isEmpty(token)) {
			return authenticationService.getErrorResponseEntity("Unauthorized Request", "Invalid token",
					HttpStatus.UNAUTHORIZED);
		}

		boolean isTokenValid = false;

		try {
			isTokenValid = authenticationService.validateToken(token, username);

		} catch (Exception e) {
			new ResponseEntity<>(new ErrorResponseVO("Unauthorized Request", "Invalid token"), HttpStatus.UNAUTHORIZED);
		}

		if (isTokenValid) {
			List<RestaurantChoiceVO> restaurantChoiceVOList = restaurantChoiceService.getAllChoicesByUserId(username);
			return new ResponseEntity<>(restaurantChoiceVOList, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(new ErrorResponseVO("Unauthorized Request", "Invalid token"),
					HttpStatus.UNAUTHORIZED);
		}
	}

	@CrossOrigin
	@GetMapping("/get-random-choice/{username}")
	public ResponseEntity<?> getRandomChoice(@PathVariable("username") String username,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
		if (authenticationService.isEmpty(authorization)) {
			return authenticationService.getErrorResponseEntity("Unauthorized Request", "Invalid token",
					HttpStatus.UNAUTHORIZED);
		}
		String token = authenticationService.extractToken(authorization);

		if (authenticationService.isEmpty(token)) {
			return authenticationService.getErrorResponseEntity("Unauthorized Request", "Invalid token",
					HttpStatus.UNAUTHORIZED);
		}

		boolean isTokenValid = false;

		try {
			isTokenValid = authenticationService.validateToken(token, username);

		} catch (Exception e) {
			new ResponseEntity<>(new ErrorResponseVO("Unauthorized Request", "Invalid token"), HttpStatus.UNAUTHORIZED);
		}

		if (isTokenValid) {
			RestaurantChoiceVO restaurantChoiceVO = restaurantChoiceService.getRandomPick(username);
			return new ResponseEntity<>(restaurantChoiceVO, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(new ErrorResponseVO("Unauthorized Request", "Invalid token"),
					HttpStatus.UNAUTHORIZED);
		}
	}
}
