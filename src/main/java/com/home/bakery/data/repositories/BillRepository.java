package com.home.bakery.data.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.home.bakery.data.entities.Bill;
@Repository
@EnableJpaRepositories
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("SELECT b FROM Bill b WHERE b.userDetail.address LIKE %:address% AND b.deliveryDate = CURRENT_DATE")
    List<Bill> findByUserDetailAddress(@Param("address") String address);
}
