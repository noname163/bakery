package com.home.bakery.data.dto.response;

import java.util.Set;

import org.springframework.data.elasticsearch.annotations.Document;

import com.home.bakery.data.constans.ProductStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document(indexName = "product_index")
public class ProductResponse {
    private Long id;
    private String name;
    private Set<String> images;
    private Integer expiredDate;
    private ProductStatus status;
    private String category;
    private Double price;
}

