package com.home.bakery.services.user;

import com.home.bakery.data.constans.UserRole;
import com.home.bakery.data.dto.request.ResetPasswordRequest;
import com.home.bakery.data.dto.request.UserRequest;

public interface UserService {
    public void createUser(UserRequest userRequest, UserRole userRole);
    public void forgotPassword(String email);
    public void resetPassword(ResetPasswordRequest resetPasswordRequest);
}
