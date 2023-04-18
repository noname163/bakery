package com.home.bakery.services.authen;

import com.home.bakery.data.entities.User;

public interface SecurityContextService {
    public void setSecurityContext(String username);

    public User getCurrentUser();

}
