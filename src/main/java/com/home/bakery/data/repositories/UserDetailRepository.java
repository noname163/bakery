package com.home.bakery.data.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.bakery.data.entities.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    public List<UserDetail> findByIdIn(Set<Long> ids);
}
