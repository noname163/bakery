package com.home.bakery.data.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BillDetailResponse {
    private String productName;
    private int quantity;
    private int exchangeQuantity;
}
