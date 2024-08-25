package com.home.bakery.services.billdetail.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.dto.request.BillDetailRequest;
import com.home.bakery.data.dto.request.BillDetailUpdateRequest;
import com.home.bakery.data.dto.response.BillDetailResponse;
import com.home.bakery.data.entities.Bill;
import com.home.bakery.data.entities.BillDetail;
import com.home.bakery.data.entities.Product;
import com.home.bakery.data.repositories.BillDetailRepository;
import com.home.bakery.data.repositories.ProductRepository;
import com.home.bakery.mappers.BillDetailMapper;
import com.home.bakery.services.billdetail.BillDetailService;

@Service
public class BillDetailServiceImpl implements BillDetailService {

    @Autowired
    private BillDetailRepository billDetailRepository;
    @Autowired
    private BillDetailMapper billDetailMapper;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void createBillDetails(Map<Bill, List<BillDetailRequest>> billDetailRequests) {
        Set<Long> productIds = billDetailRequests.values()
                .stream()
                .flatMap(List::stream)
                .map(BillDetailRequest::getProductId)
                .collect(Collectors.toSet());

        Map<Long, Product> products = productRepository.findByIdIn(productIds).stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        List<BillDetail> billDetails = new ArrayList<>();

        for (Map.Entry<Bill, List<BillDetailRequest>> entry : billDetailRequests.entrySet()) {
            billDetails.addAll(
                    billDetailMapper.mapBillDetailRequestsToBillDetails(entry.getValue(),
                            entry.getKey()));
        }
        for (BillDetail billDetail : billDetails) {
            Product product = products.get(billDetail.getProductId());
            billDetail.setPrice(product.getPrice()
                    - ((product.getPrice() * billDetail.getCustomerCommission()) / 100));
            billDetail.setProduct(product);
        }

        billDetailRepository.saveAll(billDetails);
    }

    @Override
    public List<BillDetailResponse> editBillDetails(List<BillDetailUpdateRequest> billDetailUpdateRequests) {
        Set<Long> setProductIds = billDetailUpdateRequests.stream().map(BillDetailUpdateRequest::getProductId)
                .filter(Objects::nonNull).collect(Collectors.toSet());
        Set<Long> setBillDetailIds = billDetailUpdateRequests.stream().map(BillDetailUpdateRequest::getId)
                .filter(Objects::nonNull).collect(Collectors.toSet());
        Map<Long, BillDetail> billDetails = billDetailRepository.findAllById(setBillDetailIds).stream()
                .collect(Collectors.toMap(BillDetail::getId, billDetail -> billDetail));
        Map<Long, Product> products = productRepository.findAllById(setProductIds).stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        Set<BillDetail> billDetailsUpdate = new HashSet<>();
        List<BillDetailResponse> billDetailResponses = new ArrayList<>();
        for (BillDetailUpdateRequest billDetailUpdateRequest : billDetailUpdateRequests) {
            BillDetail billDetail = billDetails.get(billDetailUpdateRequest.getId());
            Product product = products.get(billDetailUpdateRequest.getProductId());
            billDetailsUpdate.add(
                    billDetailMapper.mapBillDetailUpdateToBillDetail(billDetail,
                            billDetailUpdateRequest, product));
            billDetailResponses.add(
                    billDetailMapper.mapBillDetailToBillDetailResponse(billDetail,
                            product));
        }
        billDetailRepository.saveAll(billDetailsUpdate);
        return billDetailResponses;
    }

    @Override
    public List<BillDetailResponse> getListBillDetailByBillId(Long billId) {
        Optional<List<BillDetailResponse>> billDetailOtp = billDetailRepository
                .findBillDetailByBillIdCustom(billId);
        return billDetailOtp.get();
    }

}
