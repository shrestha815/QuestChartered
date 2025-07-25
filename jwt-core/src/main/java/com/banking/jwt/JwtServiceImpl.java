package com.banking.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtServiceImpl implements JwtService {
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration-ms}")
	private long jwtExpirationMs;
	
	private Key getSigninKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	@Override
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+ jwtExpirationMs))
				.signWith(getSigninKey())
				.compact();
	}

	@Override
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(getSigninKey())
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
				.setSigningKey(getSigninKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}

}
