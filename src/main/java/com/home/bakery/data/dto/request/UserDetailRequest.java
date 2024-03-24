package com.home.bakery.data.dto.request;

import lombok.Getter;

@Getter
public class UserDetailRequest {
    private String email;
    private float commissionRate;
    private String phone;
    private String shopName;
    private String addressDetail;
    private Long addressId;
}
