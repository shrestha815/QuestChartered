package com.banking.transaction.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banking.transaction.dto.TransactionRequest;
import com.banking.transaction.dto.TransactionResponse;
import com.banking.transaction.entity.Transaction;
import com.banking.transaction.enums.TransactionType;
import com.banking.transaction.repository.TransactionRepository;
import com.banking.transaction.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	private final TransactionRepository transactionRepository;
	
	@Override
	public TransactionResponse createTransaction(TransactionRequest request) {
		Transaction transaction = Transaction.builder()
                .accountId(request.getAccountId())
                .amount(request.getAmount())
                .description(request.getDescription())
                .transactionType(TransactionType.valueOf(request.getTransactionType().toUpperCase()))
                .timestamp(LocalDateTime.now())
                .build();
		
		 Transaction savedTransaction = transactionRepository.save(transaction);
		 
		return mapToResponse(savedTransaction);
	}

	@Override
	public List<TransactionResponse> getTransactionsByAccountId(Long accountId) {
		return transactionRepository.findByAccountId(accountId)
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}
	
	private TransactionResponse mapToResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccountId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .description(transaction.getDescription())
                .timestamp(transaction.getTimestamp())
                .build();
    }

}