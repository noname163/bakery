package com.home.bakery.data.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.home.bakery.data.entities.Bill;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDetailRequest {
    private Long productId;
    private Integer quantity; 
    private Integer exchangeQuantity;
    @JsonIgnore
    private Bill bill;
}
