package com.home.bakery.data.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AddressRequest {
    private Long provinceId;
    private Long stateId;
    private Long cityId;
}
