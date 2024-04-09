package com.home.bakery.data.dto.request;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {
    private String otp;
    private String password;
    private String confirmPassword;
}
