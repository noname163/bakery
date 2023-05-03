package com.home.bakery.data.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CustomerRequest {
    private String name;
    private String phone;
    private String shopName;
    private String addressDetail;
    private String type;
    private Long stateId;
    private Long provinceId;
    private Long cityId;
    private Long districtId;
}
