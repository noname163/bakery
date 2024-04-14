package com.home.bakery.services.billdetail;

import java.util.List;
import java.util.Map;

import com.home.bakery.data.dto.request.BillDetailRequest;
import com.home.bakery.data.entities.Bill;

public interface BillDetailService {
    public void createBillDetails(Map<Bill, List<BillDetailRequest>> billDetailRequests);
}
