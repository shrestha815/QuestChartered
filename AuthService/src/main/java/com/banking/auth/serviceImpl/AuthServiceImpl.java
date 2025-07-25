package com.banking.auth.serviceImpl;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.banking.auth.dto.AuthRequest;
import com.banking.auth.dto.AuthResponse;
import com.banking.auth.service.AuthService;
import com.banking.auth.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final RestTemplate restTemplate;
	
	@Value("${user.service.url}")
	private String userServiceUrl;
	
	private final JwtService jwtService;

	@Override
	public AuthResponse authenticate(AuthRequest request) {
		// Call user-service to validate credentials
		String url = userServiceUrl + "/users/authenticate";
		
		Boolean validUser = restTemplate.postForObject(url, request, Boolean.class);
		
		if(validUser == null || !validUser) {
			throw new RuntimeException("Invalid email or password");
		}
		
		String token = jwtService.generateToken(request.getEmail());
		
		return new AuthResponse(token);
	}

}
