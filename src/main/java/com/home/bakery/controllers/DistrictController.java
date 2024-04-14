package com.home.bakery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.bakery.services.address.DistrictService;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {
    @Autowired
    private DistrictService districtService;

    @PostMapping
    public ResponseEntity<Void> addDistrict(String name, Long provinceId){
        districtService.addDistrict(name, provinceId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
