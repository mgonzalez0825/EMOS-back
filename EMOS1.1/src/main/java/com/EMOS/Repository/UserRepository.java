package com.EMOS.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMOS.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
}
