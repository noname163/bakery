package com.home.bakery.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.home.bakery.data.dto.response.BillResponse;
import com.home.bakery.data.entities.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("SELECT b.id AS id, " + "ud.shopName AS customerName, " + "b.status AS billStatus, "
            + "b.deliveryDate AS deliveryDate, " + "ud.addressDetail AS address, " + "driver AS driverName "
            + "FROM Bill b " + "LEFT JOIN b.userDetail ud " + "LEFT JOIN b.user as driver "
            + "WHERE b.userDetail.address LIKE %:address% AND b.deliveryDate = CURRENT_DATE")
    List<BillResponse> getBillByAddress(@Param("address") String address);
}
