package com.banking.account.service;

import java.util.List;

import com.banking.account.dto.AccountRequest;
import com.banking.account.dto.AccountResponse;

public interface AccountService {
	AccountResponse createAccount(AccountRequest request);
	AccountResponse getAccountById(Long id);
	List<AccountResponse> getAccountByUserId(Long userId);
	void deleteAccount(Long id);
}
