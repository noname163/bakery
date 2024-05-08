package com.home.bakery.services.bill;

import java.util.List;

import com.home.bakery.data.constans.BillStatus;
import com.home.bakery.data.constans.SortType;
import com.home.bakery.data.dto.request.BillRequest;
import com.home.bakery.data.dto.response.BillResponse;
import com.home.bakery.data.dto.response.PaginationResponse;

public interface BillService {
    public List<BillResponse> createBills(List<BillRequest> billRequests);

    public PaginationResponse<List<BillResponse>> getListBills(String searchTerm, int page, int size, String sortField,SortType sortType, BillStatus status);
}
