package com.banking.account.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.dto.AccountRequest;
import com.banking.account.dto.AccountResponse;
import com.banking.account.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;
	
	@PostMapping
	public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest req) {
		return ResponseEntity.ok(accountService.createAccount(req));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(accountService.getAccountById(id));
	}
	
	@GetMapping("/account/{userId}")
	public ResponseEntity<List<AccountResponse>> getByUser(@PathVariable Long userId) {
		return ResponseEntity.ok(accountService.getAccountByUserId(userId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		accountService.deleteAccount(id);
		return ResponseEntity.noContent().build();
	}
}
