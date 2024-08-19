package com.home.bakery.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.home.bakery.data.constans.BillStatus;
import com.home.bakery.data.constans.SortType;
import com.home.bakery.data.dto.request.BillRequest;
import com.home.bakery.data.dto.response.BillResponse;
import com.home.bakery.data.dto.response.PaginationResponse;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.services.bill.BillService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    @Autowired
    private BillService billService;

    @Operation(summary = "Create bills")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create bills successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BillRequest.class)) }),
            @ApiResponse(responseCode = "400", description = "Data not valid", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @PostMapping
    public ResponseEntity<List<BillResponse>> createBill(@RequestBody List<BillRequest> billRequests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billService.createBills(billRequests));
    }

    @Operation(summary = "Get bills")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get product successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
    })
    @GetMapping
    public ResponseEntity<PaginationResponse<List<BillResponse>>> getListBill(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false) String field,
            @RequestParam(required = false, defaultValue = "ASC") SortType sortType) {
        return ResponseEntity.status(HttpStatus.OK).body(billService.getListBills(field, page, size, field, sortType, BillStatus.CREATED));
    }
}
