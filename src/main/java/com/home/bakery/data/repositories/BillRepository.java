package com.home.bakery.data.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.home.bakery.data.entities.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("SELECT b FROM Bill b WHERE b.userDetail.addressDetail LIKE %:address% AND b.deliveryDate = CURRENT_DATE")
    List<Bill> getBillByAddressAndCurrentDate(@Param("address") String address);
}
