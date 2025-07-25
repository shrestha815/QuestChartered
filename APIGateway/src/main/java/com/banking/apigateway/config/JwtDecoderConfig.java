package com.banking.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtDecoderConfig {
	
	@Value("${spring.security.oauth2.resourceserver.jwt.secret}")
	private String secret;
	
	@Bean
	public ReactiveJwtDecoder reactiveJwtDecoder() {
		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		SecretKeySpec key = new SecretKeySpec(keyBytes, "HMACSHA256");
		return NimbusReactiveJwtDecoder.withSecretKey(key).build();
	}

}
