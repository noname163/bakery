package com.home.bakery.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.home.bakery.data.dto.request.BillDetailRequest;
import com.home.bakery.data.entities.Bill;
import com.home.bakery.data.entities.BillDetail;

@Component
public class BillDetailMapper {
    public BillDetail mapBillDetailRequestToBillDetail(BillDetailRequest billDetailRequest, Bill bill) {
        return BillDetail
                .builder()
                .bill(bill)
                .productId(billDetailRequest.getProductId())
                .exchangeQuantity(billDetailRequest.getExchangeQuantity())
                .quantity(billDetailRequest.getQuantity())
                .build();
    }

    public List<BillDetail> mapBillDetailRequestsToBillDetails(List<BillDetailRequest> billDetailRequests, Bill bill) {

        return billDetailRequests.stream().map(billRequest -> mapBillDetailRequestToBillDetail(billRequest, bill))
                .collect(Collectors.toList());
    }
}
