package com.home.bakery.services.customer;

import com.home.bakery.data.dto.request.CustomerRequest;

public interface CustomerService {
    public void createCustomer(CustomerRequest customerRequest);
}
