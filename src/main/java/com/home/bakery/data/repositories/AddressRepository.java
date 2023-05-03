package com.home.bakery.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.bakery.data.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    public Optional<Address> findByStateIdAndProvinceIdAndCityId(Long stateId,
            Long provinceId,
            Long cityId);
}
