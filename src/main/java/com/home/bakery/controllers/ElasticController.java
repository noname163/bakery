package com.home.bakery.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.home.bakery.data.dto.response.PaginationResponse;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.services.elastic.ElasticSearchService;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/elastic")
public class ElasticController {
    @Autowired
    private ElasticSearchService elasticSearchService;

    @Operation(summary = "Clear data by index")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @DeleteMapping()
    public ResponseEntity<Void> clearDataByIndex(@RequestParam String index) throws ElasticsearchException, IOException{
        elasticSearchService.clearAllDataByIndex(index);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
