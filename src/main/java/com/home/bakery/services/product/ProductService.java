package com.home.bakery.services.product;

import com.home.bakery.data.constans.ProductStatus;
import com.home.bakery.data.dto.request.ProductRequest;
import com.home.bakery.data.dto.response.ProductResponse;

public interface ProductService {
    public ProductResponse createUpdateProduct(ProductRequest productRequest);
    public void changeProductStatus(long id, ProductStatus status);
}
