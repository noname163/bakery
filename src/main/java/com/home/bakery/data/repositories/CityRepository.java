package com.home.bakery.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.bakery.data.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Boolean existsByName(String name);
}
