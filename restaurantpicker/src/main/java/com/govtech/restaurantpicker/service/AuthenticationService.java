package com.govtech.restaurantpicker.service;

import java.security.Key;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.govtech.restaurantpicker.configuration.MapperConfiguration;
import com.govtech.restaurantpicker.model.RestaurantUser;
import com.govtech.restaurantpicker.model.vo.ErrorResponseVO;
import com.govtech.restaurantpicker.model.vo.RestaurantUserVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthenticationService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long jwtExpirationMs;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;
	
	@Autowired
	private MapperConfiguration mapper;

	public Boolean isAuthenticationSuccess(String userName, String password) {
		RestaurantUser user = userService.getUserByUsername(userName);
		return user != null ? passwordEncoder.matches(password, user.getPassword()) : false;
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	public RestaurantUserVO saveUser(RestaurantUser user) throws Exception {
		user.setPassword(encodePassword(user.getPassword()));
		RestaurantUser restaurantUser;
		restaurantUser = userService.saveUser(user);
		return mapper.mapRestaurantUserToRestaurantUserVO(restaurantUser);
	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String subject) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + jwtExpirationMs);
		Key secretKey = Keys.hmacShaKeyFor(secret.getBytes());

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now).setExpiration(expirationDate)
				.signWith(secretKey, SignatureAlgorithm.HS256).compact();
	}

	public boolean validateToken(String token, String currentUser) {
		final String username = extractUsername(token);
		return (username.equals(currentUser) && !isTokenExpired(token));
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		Key secretKey = Keys.hmacShaKeyFor(secret.getBytes());

		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		final Date expiration = extractExpiration(token);
		return expiration.before(new Date());
	}

	public String extractToken(String bearerToken) {
		String token = null;
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			token = bearerToken.substring(7);
		}
		if (token != null) {
			return token;
		} else {
			return null;
		}
	}

	public boolean isEmpty(String value) {
		return value == null || value.isBlank() ? true : false;
	}

	public ResponseEntity<?> getErrorResponseEntity(String message, String description, HttpStatus status) {
		return new ResponseEntity<>(new ErrorResponseVO(message, description), status);
	}

}
