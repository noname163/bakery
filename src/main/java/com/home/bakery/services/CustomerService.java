package com.home.bakery.services;

import com.home.bakery.data.dto.request.CustomerRequest;

public interface CustomerService {
    public void createCustomer(CustomerRequest customerRequest);
}
