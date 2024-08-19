package com.home.bakery.services.billdetail.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.dto.request.BillDetailRequest;
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

        for (Map.Entry<Bill,List<BillDetailRequest>> entry : billDetailRequests.entrySet()) {
            billDetails.addAll(
                    billDetailMapper.mapBillDetailRequestsToBillDetails(entry.getValue(), entry.getKey()));
        }
        for (BillDetail billDetail : billDetails) {
            Product product = products.get(billDetail.getProductId());
            billDetail.setProduct(product);
        }

        billDetailRepository.saveAll(billDetails);
    }

}
