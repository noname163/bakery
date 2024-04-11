package com.home.bakery.services.product.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.constans.ProductStatus;
import com.home.bakery.data.dto.request.ProductRequest;
import com.home.bakery.data.dto.response.ProductResponse;
import com.home.bakery.data.entities.Category;
import com.home.bakery.data.entities.Product;
import com.home.bakery.data.repositories.CategoryRepository;
import com.home.bakery.data.repositories.ProductRepository;
import com.home.bakery.exceptions.NotFoundException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.mappers.ProductMapper;
import com.home.bakery.services.product.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private Message message;

    @Override
    public ProductResponse createUpdateProduct(ProductRequest productRequest) {
        Category category = null;
        Product product = null;
        if (productRequest.getCategoryId() != null) {
            category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new NotFoundException(
                            message.objectNotFoundByIdMessage("Category", productRequest.getCategoryId())));
        }
        if (productRequest.getId() == null) {
            product = productMapper.mapProductRequestToProduct(productRequest);
        } else {
            product = productRepository.findById(productRequest.getId()).orElseThrow(
                    () -> new NotFoundException(message.objectNotFoundByIdMessage("Product", productRequest.getId())));
            product = productMapper.mapProductRequestToProduct(productRequest, product);
        }
        product.setCategory(category);
        product.setStatus(ProductStatus.AVAILABLE);
        productRepository.save(product);
        return productMapper.mapProductToProductResponse(product);
    }

    @Override
    public void changeProductStatus(long id,ProductStatus status) {
        Product product =  productRepository.findById(id).orElseThrow(
            () -> new NotFoundException(message.objectNotFoundByIdMessage("Product",id)));
        product.setStatus(status);
        productRepository.save(product);
    }

}
