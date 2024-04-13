package com.home.bakery.services.bill.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.home.bakery.data.constans.BillStatus;
import com.home.bakery.data.dto.request.BillRequest;
import com.home.bakery.data.dto.response.BillResponse;
import com.home.bakery.data.entities.Bill;
import com.home.bakery.data.entities.UserDetail;
import com.home.bakery.data.repositories.BillRepository;
import com.home.bakery.data.repositories.UserDetailRepository;
import com.home.bakery.mappers.BillMapper;
import com.home.bakery.services.bill.BillService;

public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private BillMapper billMapper;

    @Override
    public List<BillResponse> createBills(List<BillRequest> billRequests) {
        // Collect unique customer IDs
        Set<Long> customerIds = billRequests.stream()
                .map(BillRequest::getCustomerId)
                .collect(Collectors.toSet());

        // Fetch UserDetail entities for the given customer IDs
        List<UserDetail> userDetails = userDetailRepository.findByIdIn(customerIds);

        // Create a map to store bills by customer ID
        Map<Long, Bill> billsMap = new HashMap<>();

        // Populate the bills map with UserDetail and default Bill instances
        userDetails
                .forEach(user -> billsMap.computeIfAbsent(user.getId(),
                        id -> Bill.builder().userDetail(user).status(BillStatus.CREATED).build()));

        // Set delivery date for each bill
        billRequests.forEach(billRequest -> {
            Bill bill = billsMap.get(billRequest.getCustomerId());
            if (bill != null) {
                bill.setDeliveryDate(billRequest.getDeliveryDate());
            }
        });

        // Save all bills
        List<Bill> savedBills = billRepository.saveAll(billsMap.values());

        return billMapper.mapBillsToBillResponses(savedBills);
    }

}
