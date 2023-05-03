package com.home.bakery.services.address.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.entities.District;
import com.home.bakery.data.entities.State;
import com.home.bakery.data.repositories.DistrictRepository;
import com.home.bakery.data.repositories.StateRepository;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.exceptions.NotFoundException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.services.address.StateService;

@Service
public class StateServiceImpl implements StateService {
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private Message message;

    public void addState(String name){
        if(Boolean.TRUE.equals(stateRepository.existsByName(name))){
            throw new BadRequestException(message.objectExistMessage("State", name));
        }
        stateRepository.save(State.builder().name(name).build());
    }

    @Override
    public void addStateWithDistrict(String state, Long districtId) {
        Optional<District> districtOtp = districtRepository.findById(districtId);
        if(districtOtp.isEmpty()){
            throw new NotFoundException(message.objectNotFoundByIdMessage("District", districtId));
        }
        if(Boolean.TRUE.equals(stateRepository.existsByName(state))){
            throw new BadRequestException(message.objectExistMessage("State", state));
        }
        stateRepository.save(State.builder().name(state).district(districtOtp.get()).build());
    }
}
