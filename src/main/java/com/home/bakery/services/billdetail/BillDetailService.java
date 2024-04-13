package com.home.bakery.services.billdetail;

import java.util.List;

import com.home.bakery.data.dto.request.BillDetailRequest;
import com.home.bakery.data.dto.response.BillDetailResponse;

public interface BillDetailService {
    public List<BillDetailResponse> createBillDetails(List<BillDetailRequest> billDetailRequests); 
}
