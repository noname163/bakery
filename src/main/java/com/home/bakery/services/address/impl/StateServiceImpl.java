package com.home.bakery.services.address.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.entities.State;
import com.home.bakery.data.repositories.StateRepository;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.services.address.StateService;

@Service
public class StateServiceImpl implements StateService {
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private Message message;

    public void addState(String name){
        if(stateRepository.existsByName(name)){
            throw new BadRequestException(message.objectExistMessage("State", name));
        }
        stateRepository.save(State.builder().name(name).build());
    }
}
