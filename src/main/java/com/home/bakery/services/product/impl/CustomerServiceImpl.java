package com.home.bakery.services.product.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.home.bakery.data.dto.request.CustomerRequest;
import com.home.bakery.data.repositories.CustomerRepository;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.mappers.CustomerMapper;
import com.home.bakery.services.CustomerService;

public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private Message message;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public void createCustomer(CustomerRequest customerRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCustomer'");
    }
    
}
