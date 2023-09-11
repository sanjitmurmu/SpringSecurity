package com.learn.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.models.User;

public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByUsername(String username);

}
