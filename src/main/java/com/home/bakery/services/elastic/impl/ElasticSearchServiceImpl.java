package com.home.bakery.services.elastic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.dto.response.ProductResponse;
import com.home.bakery.data.entities.Product;
import com.home.bakery.data.repositories.ProductRepository;
import com.home.bakery.mappers.ProductMapper;
import com.home.bakery.services.elastic.ElasticSearchService;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public void bulkData() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = productMapper.mapProductsToProductResponses(products);
        
    }
}
