package com.home.bakery.services.userDetail.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.dto.request.UserDetailRequest;
import com.home.bakery.data.entities.Address;
import com.home.bakery.data.entities.UserDetail;
import com.home.bakery.data.repositories.AddressRepository;
import com.home.bakery.data.repositories.UserDetailRepository;
import com.home.bakery.data.repositories.UserRepository;
import com.home.bakery.exceptions.NotFoundException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.mappers.usermapper.UserDetailMapper;
import com.home.bakery.services.userDetail.UserDetailService;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    public UserDetailServiceImpl(UserDetailMapper userDetailMapper, UserDetailRepository userDetailRepository,
            UserRepository userRepository, AddressRepository addressRepository, Message message) {
        this.userDetailMapper = userDetailMapper;
        this.userDetailRepository = userDetailRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.message = message;
    }

    private UserDetailMapper userDetailMapper;
    private UserDetailRepository userDetailRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private Message message;

    @Override
    public void createUserDetail(UserDetailRequest userDetailRequest) {
        UserDetail userDetail = userDetailMapper.mapUserDetailRequestToUserDetail(userDetailRequest);
        Address address = addressRepository.findById(userDetailRequest.getAddressId()).orElseThrow(
                () -> new NotFoundException(
                        message.notFoundObjectMessage("Adrress", userDetailRequest.getAddressId())));
        if (!userDetailRequest.getEmail().isEmpty()) {
            userDetail.setUser(userRepository.findByEmail(userDetailRequest.getEmail())
                    .orElseThrow(() -> new NotFoundException(
                            message.notFoundObjectMessage("User", userDetailRequest.getEmail()))));
        }
        userDetail.setAddress(address);
        userDetailRepository.save(userDetail);
    }

    @Override
    public void mapUserDetailWithUser(String email, Long userDetailId) {
        UserDetail userDetail = userDetailRepository.findById(userDetailId)
                .orElseThrow(() -> new NotFoundException("Can not found user detail with id " + userDetailId));
        userDetail.setUser(userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Can not found user with email")));

        userDetailRepository.save(userDetail);
    }

}
