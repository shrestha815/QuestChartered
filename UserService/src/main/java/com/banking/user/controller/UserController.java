package com.banking.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.user.dto.AuthRequest;
import com.banking.user.dto.UserRequest;
import com.banking.user.dto.UserResponse;
import com.banking.user.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
		return ResponseEntity.ok(userService.createUser(request));
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/fetch-user/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {
		String token = userService.authenticate(request.getEmail(), request.getPassword());
		return ResponseEntity.ok(token);
	}
	
	@GetMapping("/secured")
	public ResponseEntity<String> securedEndpoint(){
		return ResponseEntity.ok("Authenticated successfully");
	}
	
}
