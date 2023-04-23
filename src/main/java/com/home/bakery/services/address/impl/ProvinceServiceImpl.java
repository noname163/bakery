package com.home.bakery.services.address.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.entities.Province;
import com.home.bakery.data.repositories.ProvinceRepository;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.services.address.ProvinceService;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private Message message;
    @Override
    public void addProvince(String province) {
        if(provinceRepository.existsByName(province)){
            throw new BadRequestException(message.objectExistMessage("Province", province));
        }
        provinceRepository.save(Province.builder().name(province).build());
    }
    
    
}
