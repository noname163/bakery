package com.home.bakery.data.dto.request;

import lombok.Getter;


@Getter
public class UserRequest {
    private String userName;
    private String password;
    private String confirmPassword;
    private String email;
}
