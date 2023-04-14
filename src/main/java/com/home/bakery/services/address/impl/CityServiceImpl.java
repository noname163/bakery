package com.home.bakery.services.address.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.entities.City;
import com.home.bakery.data.repositories.CityRepository;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.services.address.CityService;

import lombok.Builder;

@Service
@Builder
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private Message message;
    @Override
    public void addCity(String name) {
        if(cityRepository.existsByName(name)){
            throw new BadRequestException(message.objectExistMessage("City", name));
        }
        cityRepository.save(City.builder().name(name).build());
    }
    
}
