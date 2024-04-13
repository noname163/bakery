package com.home.bakery.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.bakery.data.entities.BillDetail;

public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {
    
}
