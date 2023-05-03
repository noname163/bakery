package com.home.bakery.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.bakery.data.entities.District;

public interface DistrictRepository extends JpaRepository<District, Long>{
    Boolean existsByName(String name);
}
