package com.banking.user.serviceImpl;

import java.util.List;


import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.user.dto.UserRequest;
import com.banking.user.dto.UserResponse;
import com.banking.user.entity.User;
import com.banking.user.repository.UserRepository;
import com.banking.user.service.UserService;
import com.banking.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	

	@Override
	public UserResponse createUser(UserRequest request) {
		User user = User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(request.getRole())
				.build();
		User savedUser = userRepository.save(user);
		return mapToResponse(savedUser);
	}

	@Override
	public List<UserResponse> getAllUsers() {
		return userRepository.findAll().stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	@Override
	public UserResponse getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("User with id: " + id + " not found."));
		return mapToResponse(user);
	}
	
	@Override
	public UserResponse getUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("User with email " + email + " not found."));
		return mapToResponse(user);
	}
	
	@Override
	public boolean validateUser(String email, String password) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("Invalid Credentials"));
		return passwordEncoder.matches(password, user.getPassword());
	}

	
	private UserResponse mapToResponse(User user) {
		return UserResponse.builder()
				.id(user.getId())
				.username(user.getUsername())
				.email(user.getEmail())
				.build();
	}

	@Override
	public String authenticate(String email, String password) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("User not found"));
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		return jwtService.generateToken(email);
	}
	
}
