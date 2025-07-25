package com.banking.transaction.service;

import java.util.List;

import com.banking.transaction.dto.TransactionRequest;
import com.banking.transaction.dto.TransactionResponse;

public interface TransactionService {
	TransactionResponse createTransaction(TransactionRequest request);
	List<TransactionResponse> getTransactionsByAccountId(Long accountId);
}
