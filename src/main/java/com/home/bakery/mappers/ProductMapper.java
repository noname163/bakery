package com.home.bakery.mappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.home.bakery.data.dto.request.ProductRequest;
import com.home.bakery.data.dto.response.ProductResponse;
import com.home.bakery.data.entities.Product;

@Component
public class ProductMapper {
    public ProductResponse mapProductToProductResponse(Product product) {
        return ProductResponse
                .builder()
                .id(product.getId())
                .name((product.getName()))
                .image(product.getImage())
                .price(product.getPrice())
                .expiredDate(product.getExpiredDate())
                .build();
    }

    public Product mapProductRequestToProduct(ProductRequest productRequest) {
        return Product
                .builder()
                .name(productRequest.getName())
                .image(productRequest.getImage())
                .expiredDate(productRequest.getExpiredDate())
                .price(productRequest.getPrice())
                .build();
    }

    public Product mapProductRequestToProduct(ProductRequest productRequest, Product product){
        product.setName(Optional.ofNullable(productRequest.getName()).orElse(product.getName()));
        product.setImage(Optional.ofNullable(productRequest.getImage()).orElse(product.getImage()));
        product.setExpiredDate(Optional.ofNullable(productRequest.getExpiredDate()).orElse(product.getExpiredDate()));
        product.setPrice(Optional.ofNullable(productRequest.getPrice()).orElse(product.getPrice()));
        return product;
    }

    public List<ProductResponse> mapProductsToProductResponses(List<Product> products) {
        return products.stream().map(this::mapProductToProductResponse).collect(Collectors.toList());
    }
}
