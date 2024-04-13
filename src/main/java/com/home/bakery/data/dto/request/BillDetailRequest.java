package com.home.bakery.data.dto.request;

import lombok.Getter;

@Getter
public class BillDetailRequest {
    private Long productId;
    private Integer quantity; 
    private Integer exchangeQuantity;
}
