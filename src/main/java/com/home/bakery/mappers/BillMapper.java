package com.home.bakery.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.home.bakery.data.dto.response.BillResponse;
import com.home.bakery.data.entities.Bill;

public class BillMapper {
    public BillResponse mapBillToBillResponse(Bill bill) {
        return BillResponse.builder().build();
    }

    public List<BillResponse> mapBillsToBillResponses(List<Bill> bills) {
        return bills.stream().map(this::mapBillToBillResponse).collect(Collectors.toList());
    }
}
