package com.banking.transaction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.banking.transaction.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long accountId;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	private BigDecimal amount;
	
	private String description;
	
	private LocalDateTime timestamp;
}
