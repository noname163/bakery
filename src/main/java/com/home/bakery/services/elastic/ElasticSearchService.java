package com.home.bakery.services.elastic;

import java.io.IOException;

import com.home.bakery.data.dto.response.CategoryResponse;
import com.home.bakery.data.dto.response.ProductResponse;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;

public interface ElasticSearchService {
    public void bulkProductsData() throws ElasticsearchException, IOException;

    public void bulkProductData(ProductResponse productResponse) throws ElasticsearchException, IOException;

    public void bulkCategoryData() throws ElasticsearchException, IOException;

    public void updateProductData(ProductResponse productResponse) throws ElasticsearchException, IOException;

    public void clearAllDataByIndex(String index) throws ElasticsearchException, IOException;

    public void updateCategoryData(CategoryResponse categoryResponse) throws ElasticsearchException, IOException;
}
