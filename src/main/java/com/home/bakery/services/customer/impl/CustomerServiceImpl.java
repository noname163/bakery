package com.home.bakery.services.customer.impl;

import java.util.Optional;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.constans.CustomerType;
import com.home.bakery.data.dto.request.CustomerRequest;
import com.home.bakery.data.entities.Address;
import com.home.bakery.data.entities.Customer;
import com.home.bakery.data.repositories.AddressRepository;
import com.home.bakery.data.repositories.CustomerRepository;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.mappers.CustomerMapper;
import com.home.bakery.services.customer.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired 
    private Message message;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public void createCustomer(CustomerRequest customerRequest) {
        Optional<Address> addressOtp = addressRepository.findByStateIdAndProvinceIdAndCityId(customerRequest.getStateId(), customerRequest.getProvinceId(), customerRequest.getCityId());
        if(addressOtp.isEmpty()){
            throw new BadRequestException(message.objectNotFoundByIdMessage("Address", customerRequest.getCityId()));
        }
        CustomerType type = EnumUtils.getEnum(CustomerType.class, customerRequest.getType().toUpperCase());
        if (type == null || (type != CustomerType.RETAIL && type != CustomerType.WHOLESALE)) {
            throw new BadRequestException(message.badValue("Customer type"));
        }
        Customer customer = customerMapper.mapDtoToEntity(customerRequest);
        customer.setAddress(addressOtp.get());
        customer.setType(type);
        customerRepository.save(customer);
    }
    
}
