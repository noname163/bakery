package com.home.bakery.mappers.usermapper;

import org.springframework.stereotype.Component;

import com.home.bakery.data.dto.request.UserRequest;
import com.home.bakery.data.entities.User;

@Component
public class UserMapper {
    public User mapUserRequestToUser(UserRequest userRequest) {
        return User
                .builder()
                .email(userRequest.getEmail())
                .username(userRequest.getUserName())
                .password(userRequest.getPassword())
                .build();
    }
}
