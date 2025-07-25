package com.banking.apigateway.filter;

import org.springframework.core.Ordered;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtClaimsForwardingFilter implements GlobalFilter {
	
	private final ReactiveJwtDecoder jwtDecoder;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String auth = exchange.getRequest().getHeaders().getFirst("Authorization");
		if(auth != null && auth.startsWith("Bearer ")) {
			String token = auth.substring(7);
			return jwtDecoder.decode(token)
					.flatMap(jwt -> {
						String userEmail = jwt.getSubject();
						List<String> roles = jwt.getClaimAsStringList("roles");
						String rolesHeader = (roles == null || roles.isEmpty())
								?""
								: String.join(",", roles);
						
						 ServerHttpRequest mutatedReq = exchange.getRequest().mutate()
								.header("X-User-Email", userEmail)
								.header("X-User-Roles", rolesHeader)
								.build();
						return chain.filter(exchange.mutate().request(mutatedReq).build());
					})
					.onErrorResume(e -> {
						exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
						return exchange.getResponse().setComplete();
					});
		}
		return chain.filter(exchange);
	}
	
	

}
