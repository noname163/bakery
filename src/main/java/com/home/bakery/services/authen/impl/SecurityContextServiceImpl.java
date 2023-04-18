package com.home.bakery.services.authen.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.home.bakery.config.CustomUserDetails;
import com.home.bakery.data.entities.User;
import com.home.bakery.data.repositories.UserRepository;
import com.home.bakery.exceptions.ForbiddenException;
import com.home.bakery.services.authen.SecurityContextService;

import java.util.Optional;

@Service
public class SecurityContextServiceImpl implements SecurityContextService {

    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void setSecurityContext(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new ForbiddenException("Invalid username in JWT.");
        }
        UserDetails userDetails = new CustomUserDetails(userOptional.get());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        securityContext.setAuthentication(usernamePasswordAuthenticationToken);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        return ((CustomUserDetails) principal).getUser();
    }
}