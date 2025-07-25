package com.banking.user.dto;

import com.banking.user.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	private String username;
	private String email;
	private String password;
	private Role role;
}
