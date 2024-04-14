package com.home.bakery.data.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;

@Getter
public class BillRequest {
    private Long customerId;
    private LocalDate deliveryDate;
    private List<BillDetailRequest> billDetailRequests;
}
