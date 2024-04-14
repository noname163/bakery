package com.home.bakery.data.dto.request;

import lombok.Getter;


@Getter
public class AddressRequest {
    private Long provinceId;
    private Long stateId;
    private Long cityId;
    private Long districtId;
}
