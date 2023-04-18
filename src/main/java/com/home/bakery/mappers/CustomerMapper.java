package com.home.bakery.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.home.bakery.data.dto.request.CustomerRequest;
import com.home.bakery.data.entities.Customer;

@Component
public class CustomerMapper {
    public Customer mapDtoToEntity(CustomerRequest customerRequest){
        return Customer.builder()
        .name(customerRequest.getName())
        .phone(customerRequest.getPhone())
        .addressDetail(customerRequest.getAddressDetail())
        .shopName(customerRequest.getShopName())
        .build();
    }

    public List<Customer> mapDtoToEntity(List<CustomerRequest> customerRequests){
        return customerRequests.stream().map(this::mapDtoToEntity).collect(Collectors.toList());
    }
}
