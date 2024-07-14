package com.home.bakery.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.bakery.data.entities.Images;

public interface ImageRepository extends JpaRepository<Images, Long>{
    
}
