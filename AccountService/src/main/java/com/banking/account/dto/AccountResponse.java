package com.banking.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
	private Long id;
	private String accountNumber;
	private Long userId;
	private String accountType;
	private BigDecimal balance;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
