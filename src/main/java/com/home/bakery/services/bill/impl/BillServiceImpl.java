package com.home.bakery.services.bill.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.constans.BillStatus;
import com.home.bakery.data.dto.request.BillDetailRequest;
import com.home.bakery.data.dto.request.BillRequest;
import com.home.bakery.data.dto.response.BillResponse;
import com.home.bakery.data.entities.Bill;
import com.home.bakery.data.entities.BillDetail;
import com.home.bakery.data.entities.UserDetail;
import com.home.bakery.data.repositories.BillRepository;
import com.home.bakery.data.repositories.UserDetailRepository;
import com.home.bakery.mappers.BillMapper;
import com.home.bakery.services.bill.BillService;
import com.home.bakery.services.billdetail.BillDetailService;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private BillDetailService billDetailService;

    @Override
    @Transactional
    public List<BillResponse> createBills(List<BillRequest> billRequests) {
        Set<Long> customerIds = billRequests.stream()
                .map(BillRequest::getCustomerId)
                .collect(Collectors.toSet());

        List<UserDetail> userDetails = userDetailRepository.findByIdIn(customerIds);

        Map<Long, Bill> billsMap = new HashMap<>();

        userDetails
                .forEach(user -> billsMap.computeIfAbsent(user.getId(),
                        id -> Bill.builder().userDetail(user).createdDate(LocalDate.now()).status(BillStatus.CREATED).build()));

        billRequests.forEach(billRequest -> {
            Bill bill = billsMap.get(billRequest.getCustomerId());
            if (bill != null) {
                bill.setDeliveryDate(billRequest.getDeliveryDate());
            }
        });

        Map<Bill, List<BillDetailRequest>> billDetailRequest = new HashMap<>();

        List<Bill> savedBills = billRepository.saveAll(billsMap.values());

        for(int i=0; i<billRequests.size();i++){
            if(billRequests.get(i).getCustomerId()==savedBills.get(i).getUserDetail().getId()){
                billDetailRequest.put(savedBills.get(i), billRequests.get(i).getBillDetailRequests());
            }
        }

        billDetailService.createBillDetails(billDetailRequest);

        return billMapper.mapBillsToBillResponses(savedBills);
    }

}
