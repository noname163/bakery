package com.home.bakery.data.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.home.bakery.data.dto.response.BillDetailResponse;
import com.home.bakery.data.entities.BillDetail;

public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {
    @Query("SELECT new com.home.bakery.data.dto.response.BillDetailResponse(bd.id, product.id, product.name, " +
            "bd.price, bd.quantity, bd.exchangeQuantity, bd.createdDate, bd.updatedDate) " +
            "FROM BillDetail bd " +
            "LEFT JOIN bd.product product WHERE bd.bill.id = :id")
    Optional<List<BillDetailResponse>> findBillDetailByBillIdCustom(@Param("id") Long id);

}
