package com.home.bakery.data.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequest {
    private Long id;
    private String name;
    private String image;
    private Integer expiredDate;
    private Long categoryId;
    private Double price;
}
