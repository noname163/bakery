package com.home.bakery.data.dto.response;

import java.time.LocalDate;

import com.home.bakery.data.constans.BillStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BillResponse {
    private long id;
    private String customerName;
    private Long money;
    private BillStatus status;
    private LocalDate deliveryDate;
    private String address;
    private String driverName;
}
