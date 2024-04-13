package com.home.bakery.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.bakery.data.entities.Bill;

public interface BillRepository extends JpaRepository<Bill,Long> {
    
}
