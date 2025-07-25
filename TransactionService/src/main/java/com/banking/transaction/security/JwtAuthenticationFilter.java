package com.banking.transaction.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.banking.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	
	protected void doFilterInternal(HttpServletRequest req,
			HttpServletResponse res,
			FilterChain chain) 
	throws ServletException,IOException {
		String auth = req.getHeader("Authorization");
		if(auth!=null && auth.startsWith("Bearer ")) {
			String token = auth.substring(7);
			if(jwtService.validateToken(token)) {
				String email = jwtService.extractEmail(token);
				var authToken = new UsernamePasswordAuthenticationToken(
						email, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
				);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		chain.doFilter(req, res);
	}
}
