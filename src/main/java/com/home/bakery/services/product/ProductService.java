package com.home.bakery.services.product;

import java.util.List;

import com.home.bakery.data.constans.ProductStatus;
import com.home.bakery.data.constans.SortType;
import com.home.bakery.data.dto.request.ProductRequest;
import com.home.bakery.data.dto.response.PaginationResponse;
import com.home.bakery.data.dto.response.ProductResponse;

public interface ProductService {
    public ProductResponse createUpdateProduct(ProductRequest productRequest);

    public void changeProductStatus(long id, ProductStatus status);

    public ProductResponse setImageForProductResponse(ProductResponse productResponse);

    public PaginationResponse<List<ProductResponse>> getProducts(Integer page,
            Integer size, String field, SortType sortType);
}
