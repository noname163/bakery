package com.home.bakery.mappers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.home.bakery.data.dto.request.BillDetailRequest;
import com.home.bakery.data.dto.request.BillDetailUpdateRequest;
import com.home.bakery.data.dto.response.BillDetailResponse;
import com.home.bakery.data.entities.Bill;
import com.home.bakery.data.entities.BillDetail;
import com.home.bakery.data.entities.Product;

@Component
public class BillDetailMapper {
    public BillDetail mapBillDetailRequestToBillDetail(BillDetailRequest billDetailRequest, Bill bill) {
        return BillDetail
                .builder()
                .bill(bill)
                .productId(billDetailRequest.getProductId())
                .customerCommission(bill.getCustomerCommission())
                .exchangeQuantity(billDetailRequest.getExchangeQuantity())
                .quantity(billDetailRequest.getQuantity())
                .build();
    }

    public List<BillDetail> mapBillDetailRequestsToBillDetails(List<BillDetailRequest> billDetailRequests, Bill bill) {
        return billDetailRequests.stream().map(billRequest -> mapBillDetailRequestToBillDetail(billRequest, bill))
                .collect(Collectors.toList());
    }

    public BillDetail mapBillDetailUpdateToBillDetail(BillDetail billDetail,
            BillDetailUpdateRequest billDetailUpdateRequest, Product product) {
        billDetail.setProduct(Optional.ofNullable(product).orElse(billDetail.getProduct()));
        billDetail.setExchangeQuantity(Optional.ofNullable(billDetailUpdateRequest.getExchangeQuantity())
                .orElse(billDetail.getExchangeQuantity()));
        billDetail.setQuantity(
                Optional.ofNullable(billDetailUpdateRequest.getQuantity()).orElse(billDetail.getQuantity()));
        billDetail.setUpdatedDate(LocalDate.now());
        return billDetail;
    }

    public BillDetailResponse mapBillDetailToBillDetailResponse(BillDetail billDetail, Product product) {
        return BillDetailResponse
                .builder()
                .id(billDetail.getId())
                .exchangeQuantity(billDetail.getExchangeQuantity())
                .quantity(billDetail.getQuantity())
                .productName(product.getName())
                .productId(product.getId())
                .build();
    }
}
