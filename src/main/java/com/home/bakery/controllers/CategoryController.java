package com.home.bakery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.bakery.data.dto.request.BillRequest;
import com.home.bakery.data.dto.request.CategoryRequest;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.services.category.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Create category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create bills successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryRequest.class)) }),
            @ApiResponse(responseCode = "400", description = "Data not valid", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
