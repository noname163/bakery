package com.home.bakery.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.bakery.data.entities.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long>{
    Boolean existsByName(String name);
    public Optional<Province> findByAddresses(Long addressId);
}
