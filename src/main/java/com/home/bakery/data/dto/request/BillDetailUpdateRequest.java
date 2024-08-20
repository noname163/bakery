package com.home.bakery.data.dto.request;

import lombok.Getter;

@Getter
public class BillDetailUpdateRequest {
    private Long id;
    private Long productId;
    private Integer exchangeQuantity;
    private Integer quantity;
}
