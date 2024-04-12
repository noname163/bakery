package com.home.bakery.data.dto.response;

import com.home.bakery.data.constans.ProductStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
    private long id;
    private String name;
    private String image;
    private Integer expiredDate;
    private ProductStatus status;
    private String category;
    private Double price;
}

