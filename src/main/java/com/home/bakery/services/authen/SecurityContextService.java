package com.home.bakery.services.authen;

import com.home.bakery.data.dto.request.LoginRequest;
import com.home.bakery.data.dto.response.LoginResponse;
import com.home.bakery.data.entities.User;

public interface SecurityContextService {
    public void setSecurityContext(String username);

    public User getCurrentUser();

    public LoginResponse login(LoginRequest loginRequest);
}
