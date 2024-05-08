package com.home.bakery.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.home.bakery.data.dto.request.BillRequest;
import com.home.bakery.data.dto.response.BillResponse;
import com.home.bakery.data.entities.Bill;
import com.home.bakery.data.objects.interfaces.BillInterface;

@Component
public class BillMapper extends MapperTemplate<Bill, BillRequest, BillResponse, BillInterface> {

    @Override
    public Bill mapRequestToEntity(BillRequest object) {
        return Bill
                .builder()
                .deliveryDate(object.getDeliveryDate())
                .build();
    }

    @Override
    public BillResponse mapEntityToResponse(Bill object) {
        return BillResponse
                .builder()
                .deliveryDate(object.getDeliveryDate())
                .customerName(object.getUserDetail().getShopName())
                .address(object.getUserDetail().getAddressDetail())
                .status(object.getStatus())
                .build();
    }

    @Override
    public BillResponse mapInterfaceToResponse(BillInterface object) {
        if (object == null) {
            return null;
        }
        return BillResponse
                .builder()
                .id(object.getId())
                .address(object.getAddress())
                .customerName(object.getCustomerName())
                .driverName(object.getDriverName())
                .deliveryDate(object.getDeliveryDate())
                .money(object.getMoney())
                .status(object.getStatus())
                .build();
    }

}
