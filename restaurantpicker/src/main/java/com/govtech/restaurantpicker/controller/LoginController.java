package com.govtech.restaurantpicker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.govtech.restaurantpicker.model.RestaurantUser;
import com.govtech.restaurantpicker.model.vo.AuthenticationVO;
import com.govtech.restaurantpicker.service.AuthenticationService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private AuthenticationService authenticationService;

	@CrossOrigin
	@PostMapping("/user")
	public ResponseEntity<?> login(@RequestBody RestaurantUser user) {
		Boolean userFromDB = authenticationService.isAuthenticationSuccess(user.getUsername(), user.getPassword());
		if (userFromDB) {
			String token = "";
			try {
				token = authenticationService.generateToken(user.getUsername());

			} catch (Exception e) {

				return authenticationService.getErrorResponseEntity("Unauthorized Request", "Cannot create the token.",
						HttpStatus.UNAUTHORIZED);
			}

			return new ResponseEntity<>(new AuthenticationVO(user.getUsername(), token), HttpStatus.OK);
		} else {
			return authenticationService.getErrorResponseEntity("Unauthorized Request",
					"The username or password you entered is incorrect.", HttpStatus.UNAUTHORIZED);

		}

	}

	@CrossOrigin
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody RestaurantUser user) {

		try {
			return new ResponseEntity<>(authenticationService.saveUser(user), HttpStatus.OK);
		} catch (Exception e) {
			return authenticationService.getErrorResponseEntity("Database Error", "User already exist in the system",
					HttpStatus.BAD_REQUEST);
		}
	}
}
