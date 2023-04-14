package com.home.bakery.services.address.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.entities.Province;
import com.home.bakery.data.repositories.ProvinceRepository;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.exceptions.message.Message;

@Service
public class ProvinceServiceImpl {
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private Message message;
    public void addProvince(String name){
        if(provinceRepository.existsByName(name)){
            throw new BadRequestException(message.objectExistMessage("Province", name));
        }
        provinceRepository.save(Province.builder().name(name).build());
    }
}
