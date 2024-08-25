package com.home.bakery.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.bakery.data.dto.request.BillDetailUpdateRequest;
import com.home.bakery.data.dto.request.BillRequest;
import com.home.bakery.data.dto.response.BillDetailResponse;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.services.billdetail.BillDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController 
@RequestMapping("/api/bill-details")
public class BillDetailController {

    @Autowired
    private BillDetailService billDetailService;

    @Operation(summary = "Edit bill detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Edit bills detail successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BillRequest.class)) }),
            @ApiResponse(responseCode = "400", description = "Data not valid", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @PostMapping
    public ResponseEntity<List<BillDetailResponse>> editBillDetail(
            @RequestBody List<BillDetailUpdateRequest> billDetailUpdateRequests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billDetailService.editBillDetails(billDetailUpdateRequests));
    }
}
