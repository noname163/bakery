package com.home.bakery.services.address.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.entities.City;
import com.home.bakery.data.entities.Province;
import com.home.bakery.data.repositories.CityRepository;
import com.home.bakery.data.repositories.ProvinceRepository;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.exceptions.NotFoundException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.services.address.CityService;

import lombok.Builder;

@Service
@Builder
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private Message message;
    @Override
    public void addCity(String name) {
        if(Boolean.TRUE.equals(cityRepository.existsByName(name))){
            throw new BadRequestException(message.objectExistMessage("City", name));
        }
        cityRepository.save(City.builder().name(name).build());
    }
    @Override
    public void addCityWithProvince(String city, Long provinceId) {
        Optional<Province> provinceOtp = provinceRepository.findById(provinceId);
        if(provinceOtp.isEmpty()){
            throw new NotFoundException(message.objectNotFoundByIdMessage("Province", provinceId));
        }
        if(Boolean.TRUE.equals(cityRepository.existsByName(city))){
            throw new BadRequestException(message.objectExistMessage("City", city));
        }
        cityRepository.save(City.builder().name(city).province(provinceOtp.get()).build());
    }
    
}
