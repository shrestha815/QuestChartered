package com.banking.auth.serviceImpl;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

import com.banking.auth.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {
	private final Key key;
	private final long jwtExpirationMs;
	
	public JwtServiceImpl(
	        @Value("${jwt.secret}") String secret,
	        @Value("${jwt.expiration-ms}") long expirationMs) {
	        this.key = Keys.hmacShaKeyFor(secret.getBytes());
	        this.jwtExpirationMs = expirationMs;
	    }
	

	@Override
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(key)
				.compact();
	}

	@Override
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
		 return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String extractEmail(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}

}
