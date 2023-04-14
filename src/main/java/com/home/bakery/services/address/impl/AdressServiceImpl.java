package com.home.bakery.services.address.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.entities.Address;
import com.home.bakery.data.entities.City;
import com.home.bakery.data.repositories.AddressRepository;
import com.home.bakery.data.repositories.CityRepository;
import com.home.bakery.data.repositories.ProvinceRepository;
import com.home.bakery.data.repositories.StateRepository;
import com.home.bakery.exceptions.NotFoundException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.services.address.AddressService;

@Service
public class AdressServiceImpl implements AddressService{
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private Message message;
    @Override
    public void CreateAddress(Long provinceId, Long stateId, Long cityId) {
        if(!provinceRepository.existsById(provinceId)){
            throw new NotFoundException(message.objectNotFoundByIdMessage("Province", provinceId));
        }
        if(!cityRepository.existsById(cityId)){
            throw new NotFoundException(message.objectNotFoundByIdMessage("City", cityId));
        }
        if(!stateRepository.existsById(stateId)){
            throw new NotFoundException(message.objectNotFoundByIdMessage("State", stateId));
        }
        addressRepository.save(Address.builder()
        .city(cityRepository.findById(cityId).get())
        .province(provinceRepository.findById(provinceId).get())
        .state(stateRepository.findById(stateId).get())
        .build());
    }
    
}
