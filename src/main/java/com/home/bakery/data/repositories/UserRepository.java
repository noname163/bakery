package com.home.bakery.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.bakery.data.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String name);
}
