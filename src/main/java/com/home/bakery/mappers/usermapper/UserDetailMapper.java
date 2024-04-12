package com.home.bakery.mappers.usermapper;

import org.springframework.stereotype.Component;

import com.home.bakery.data.dto.request.UserDetailRequest;
import com.home.bakery.data.entities.UserDetail;

@Component
public class UserDetailMapper {
    public UserDetail mapUserDetailRequestToUserDetail(UserDetailRequest userDetailRequest) {
        return UserDetail
                .builder()
                .shopName(userDetailRequest.getShopName())
                .phone(userDetailRequest.getPhone())
                .commissionRate(userDetailRequest.getCommissionRate())
                .addressDetail(userDetailRequest.getAddressDetail())
                .build();
    }
}
