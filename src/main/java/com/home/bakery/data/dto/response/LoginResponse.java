package com.home.bakery.data.dto.response;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
}
