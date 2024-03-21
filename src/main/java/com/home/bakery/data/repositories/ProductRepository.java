package com.home.bakery.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.bakery.data.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
