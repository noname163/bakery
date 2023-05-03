package com.home.bakery.services.address.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.dto.request.AddressRequest;
import com.home.bakery.data.entities.Address;
import com.home.bakery.data.entities.City;
import com.home.bakery.data.entities.District;
import com.home.bakery.data.entities.Province;
import com.home.bakery.data.entities.State;
import com.home.bakery.data.repositories.AddressRepository;
import com.home.bakery.data.repositories.CityRepository;
import com.home.bakery.data.repositories.DistrictRepository;
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
    private DistrictRepository districtRepository;
    @Autowired
    private Message message;
    @Override
    public void createAddress(AddressRequest addressRequest) {
        Optional<City> cityOtp = cityRepository.findById(addressRequest.getCityId());

        if(cityOtp.isEmpty()){
            throw new NotFoundException(message.objectNotFoundByIdMessage("Province", addressRequest.getProvinceId()));
        }
        Optional<Province> provinceOtp = provinceRepository.findById(addressRequest.getProvinceId());
        if(provinceOtp.isEmpty()){
            throw new NotFoundException(message.objectNotFoundByIdMessage("City", addressRequest.getCityId()));
        }
        Optional<State> stateOtp = stateRepository.findById(addressRequest.getStateId());
        if(stateOtp.isEmpty()){
            throw new NotFoundException(message.objectNotFoundByIdMessage("State", addressRequest.getStateId()));
        }
        Optional<District> districtOtp = districtRepository.findById(addressRequest.getDistrictId());
        if(districtOtp.isEmpty()){
            throw new NotFoundException(message.objectNotFoundByIdMessage("District", addressRequest.getDistrictId()));
        }

        addressRepository.save(Address.builder()
        .city(cityOtp.get())
        .province(provinceOtp.get())
        .state(stateOtp.get())
        .district(districtOtp.get())
        .build());
    }
    
}
