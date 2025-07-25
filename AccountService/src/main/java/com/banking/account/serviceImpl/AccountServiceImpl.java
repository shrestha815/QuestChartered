package com.banking.account.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banking.account.dto.AccountRequest;
import com.banking.account.dto.AccountResponse;
import com.banking.account.entity.Account;
import com.banking.account.repository.AccountRepository;
import com.banking.account.service.AccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	
	private final AccountRepository accountRepository;

	@Override
	public AccountResponse createAccount(AccountRequest request) {
		String generatedAccountNumber = generateAccountNumber();
		
		Account account = Account.builder()
				.accountNumber(generatedAccountNumber)
				.userId(request.getUserId())
				.accountType(request.getAccountType())
				.balance(request.getInitialDeposit())
				.build();
		Account saved = accountRepository.save(account);
		return mapToResponse(saved);
	}


	private String generateAccountNumber() {
		String accountNumber;
		do {
			accountNumber = String.valueOf(1000000000L + new java.util.Random().nextLong(9000000000L));
		} while(accountRepository.existsByAccountNumber(accountNumber));
		return accountNumber;
	}

	@Override
	public AccountResponse getAccountById(Long id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Account not found with id: " + id));
		return mapToResponse(account);
	}

	@Override
	public List<AccountResponse> getAccountByUserId(Long userId) {
		return accountRepository.findByUserId(userId)
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteAccount(Long id) {
		accountRepository.deleteById(id);
	}
	
	private AccountResponse mapToResponse(Account account) {
		return AccountResponse.builder()
				.id(account.getId())
				.accountNumber(account.getAccountNumber())
				.userId(account.getUserId())
				.accountType(account.getAccountType())
				.balance(account.getBalance())
				.build();
	}

}
