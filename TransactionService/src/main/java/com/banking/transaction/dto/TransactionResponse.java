package com.banking.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.banking.transaction.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
	private Long id;
	private Long accountId;
	private TransactionType transactionType;
	private BigDecimal amount;
	private String description;
	private LocalDateTime timestamp;
}
