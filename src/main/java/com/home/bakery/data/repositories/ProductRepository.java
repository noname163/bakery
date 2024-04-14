package com.home.bakery.data.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.bakery.data.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByIdIn(Set<Long> ids);
}
