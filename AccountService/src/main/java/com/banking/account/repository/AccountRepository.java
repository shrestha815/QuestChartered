package com.banking.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.banking.account.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	//lookup an account by its number
	Optional<Account> findByAccountNumber(String accountNumber);
	//fetch all accounts for a user
	List<Account> findByUserId(Long userId);
	boolean existsByAccountNumber(String accountNumber);
}
