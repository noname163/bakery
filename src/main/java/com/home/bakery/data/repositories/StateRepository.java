package com.home.bakery.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.bakery.data.entities.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    Boolean existsByName(String name);
    public Optional<State> findByAddress(Long addressId);
}
