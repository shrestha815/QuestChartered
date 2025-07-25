package com.banking.user.service;

import java.util.List;

import com.banking.user.dto.UserRequest;
import com.banking.user.dto.UserResponse;

public interface UserService {
	UserResponse createUser(UserRequest request);
	List<UserResponse> getAllUsers();
	UserResponse getUserById(Long id);
	UserResponse getUserByEmail(String email);
	boolean validateUser(String email, String password);
	String authenticate(String email, String password);
}