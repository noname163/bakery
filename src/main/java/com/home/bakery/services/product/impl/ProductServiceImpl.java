package com.home.bakery.services.product.impl;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.home.bakery.data.constans.ProductStatus;
import com.home.bakery.data.constans.SortType;
import com.home.bakery.data.dto.request.ProductRequest;
import com.home.bakery.data.dto.response.PaginationResponse;
import com.home.bakery.data.dto.response.ProductResponse;
import com.home.bakery.data.entities.Category;
import com.home.bakery.data.entities.Product;
import com.home.bakery.data.repositories.CategoryRepository;
import com.home.bakery.data.repositories.ProductRepository;
import com.home.bakery.exceptions.NotFoundException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.mappers.ProductMapper;
import com.home.bakery.services.aws.AWSService;
import com.home.bakery.services.image.ImageService;
import com.home.bakery.services.product.ProductService;
import com.home.bakery.utils.PageableUtil;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private CategoryRepository categoryRepository;
    private PageableUtil pageableUtil;
    private Message message;
    private ImageService imageService;
    private AWSService awsService;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
            CategoryRepository categoryRepository,
            PageableUtil pageableUtil, Message message, ImageService imageService, AWSService awsService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.pageableUtil = pageableUtil;
        this.message = message;
        this.imageService = imageService;
        this.awsService = awsService;
    }

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
    public void changeProductStatus(long id, ProductStatus status) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException(message.objectNotFoundByIdMessage("Product", id)));
        product.setStatus(status);
        productRepository.save(product);
    }

    @Override
    public PaginationResponse<List<ProductResponse>> getProducts(Integer page, Integer size, String field,
            SortType sortType) {
        Pageable pageable = pageableUtil.getPageable(page, size, field, sortType);

        Page<Product> data = productRepository.findAll(pageable);
        List<ProductResponse> productResponses = productMapper.mapProductsToProductResponses(data.getContent());
        for (ProductResponse productResponse : productResponses) {
            setImageForProductResponse(productResponse);
        }
        return PaginationResponse.<List<ProductResponse>>builder()
                .data(productResponses)
                .totalPage(data.getTotalPages())
                .totalRow(data.getTotalElements())
                .build();

    }

    @Override
    public ProductResponse setImageForProductResponse(ProductResponse productResponse) {
        Set<String> imageNames = imageService.getListImageNameByProductId(productResponse.getId());
        productResponse.setImages(awsService.getFileUrls(imageNames));
        return productResponse;
    }

}
