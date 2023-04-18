package com.home.bakery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.home.bakery.data.dto.request.AddressRequest;
import com.home.bakery.exceptions.NotFoundException;
import com.home.bakery.services.address.AddressService;
import com.home.bakery.services.address.StateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private StateService stateService;

    @Operation(summary = "Create new address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Create address successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AddressRequest.class))}),
            @ApiResponse(responseCode = "404", description = "Not found information you provide", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))})
    })
    @PostMapping()
    public ResponseEntity<Void> createAddress(@RequestBody AddressRequest addressRequest){
        addressService.CreateAddress(addressRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/state")
    public ResponseEntity<Void> createState(@RequestParam String state){
        stateService.addState(state);
        return ResponseEntity.noContent().build();
    }
}
