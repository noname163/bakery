package com.home.bakery.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.home.bakery.data.constans.ImageTypes;
import com.home.bakery.data.constans.SortType;
import com.home.bakery.data.dto.request.ProductRequest;
import com.home.bakery.data.dto.request.UserRequest;
import com.home.bakery.data.dto.response.PaginationResponse;
import com.home.bakery.data.dto.response.ProductResponse;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.services.elastic.ElasticSearchService;
import com.home.bakery.services.image.ImageService;
import com.home.bakery.services.product.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ElasticSearchService elasticSearchService;

    @Operation(summary = "Create new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create product successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProductRequest.class)) }),
            @ApiResponse(responseCode = "400", description = "Data not valid", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @PostMapping
    @Transactional
    public ResponseEntity<ProductResponse> createProduct(@RequestPart ProductRequest product, @RequestPart List<MultipartFile> multipartFiles) {
        ProductResponse productResponse = productService.createUpdateProduct(product);
        imageService.saveImages(productResponse, ImageTypes.PRODUCT, multipartFiles);
        productResponse = productService.setImageForProductResponse(productResponse);
        elasticSearchService.sendDataProductToElastic(productResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @Operation(summary = "Get produt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get product successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @GetMapping
    public ResponseEntity<PaginationResponse<List<ProductResponse>>> getListProduct(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false) String field,
            @RequestParam(required = false, defaultValue = "ASC") SortType sortType) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts(page, size, field, sortType));
    }
}
