package com.home.bakery.data.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailResponse {
    private long id;
    private long productId;
    private String productName;
    private double price;
    private int quantity;
    private int exchangeQuantity;
    private LocalDate createdDate;
    private LocalDate updatedDate;
}
