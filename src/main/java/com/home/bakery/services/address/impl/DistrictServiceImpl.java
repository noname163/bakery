package com.home.bakery.services.address.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.entities.District;
import com.home.bakery.data.entities.Province;
import com.home.bakery.data.repositories.DistrictRepository;
import com.home.bakery.data.repositories.ProvinceRepository;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.exceptions.NotFoundException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.services.address.DistrictService;

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private Message message;

    @Override
    public void addDistrict(String name, Long provinceId) {
        Optional<Province> provinceOtp = provinceRepository.findById(provinceId);
        if (provinceOtp.isEmpty()) {
            throw new NotFoundException(message.objectNotFoundByIdMessage("Province",
                    provinceId));
        }
        if (Boolean.TRUE.equals(districtRepository.existsByName(name))) {
            throw new BadRequestException(message.objectExistMessage("District",
                    name));
        }
        districtRepository.save(District.builder()
                .name(name)
                .province(provinceOtp.get())
                .build());
    }

}
