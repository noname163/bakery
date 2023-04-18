package com.home.bakery.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.bakery.data.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
}
