package com.home.bakery.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.bakery.data.entities.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    
}
