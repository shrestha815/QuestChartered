package com.banking.jwt;

public interface JwtService {
	String generateToken(String email);
	boolean validateToken(String token);
	String extractEmail(String token);
}
