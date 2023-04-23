package com.home.bakery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.bakery.data.dto.request.AddressRequest;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.services.address.ProvinceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @Operation(summary = "Create new province")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create province successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Psrovince was exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class))})
    })
    public ResponseEntity<Void> createProvince(String povince){
        provinceService.addProvince(povince);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
