package com.home.bakery.services.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.home.bakery.data.constans.UserRole;
import com.home.bakery.data.dto.request.ResetPasswordRequest;
import com.home.bakery.data.dto.request.UserRequest;
import com.home.bakery.data.entities.User;
import com.home.bakery.data.repositories.UserRepository;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.mappers.usermapper.UserMapper;
import com.home.bakery.services.user.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder,
    Message message) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.message = message;
    }
    private UserMapper userMapper;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Message message;

    @Override
    public void createUser(UserRequest userRequest, UserRole userRole) {
        if(!userRequest.getConfirmPassword().equals(userRequest.getPassword())){
            throw new BadRequestException("Password did not matched.");
        }
        User user = userMapper.mapUserRequestToUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(userRole);
        userRepository.save(user);
    }

    @Override
    public void forgotPassword(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'forgotPassword'");
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetPassword'");
    }
    
}
