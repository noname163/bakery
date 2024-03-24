package com.home.bakery.services.userDetail;

import com.home.bakery.data.dto.request.UserDetailRequest;

public interface UserDetailService {
    public void createUserDetail(UserDetailRequest userDetailRequest);
    public void mapUserDetailWithUser(String email, Long userDetailId);
}
