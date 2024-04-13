package com.home.bakery.services.bill;

import java.util.List;

import com.home.bakery.data.dto.request.BillRequest;
import com.home.bakery.data.dto.response.BillResponse;

public interface BillService {
    public List<BillResponse> createBills(List<BillRequest> billRequests);
}
