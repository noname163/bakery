package com.home.bakery.utils;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class EnvironmentVariable {

    @Value("${jwt.secret-key}")
    private String jwtSecret;

    @Value("${jwt.expires-time}")
    private long expireTime;

    @Value("${authentication.whitelistedUris}")
    private List<String> whiteListUrls;
}
