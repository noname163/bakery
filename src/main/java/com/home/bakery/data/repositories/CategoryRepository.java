package com.home.bakery.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.bakery.data.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
