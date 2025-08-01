package com.banking.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
