package com.home.bakery.data.repositories;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.home.bakery.data.entities.BillDetail;

public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {

}
