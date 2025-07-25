package com.banking.auth.service;

import com.banking.auth.dto.AuthRequest;
import com.banking.auth.dto.AuthResponse;

public interface AuthService {
	AuthResponse authenticate(AuthRequest request);
}
