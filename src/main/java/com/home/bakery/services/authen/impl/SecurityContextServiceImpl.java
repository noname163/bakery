package com.home.bakery.services.authen.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.home.bakery.config.CustomUserDetails;
import com.home.bakery.data.dto.request.LoginRequest;
import com.home.bakery.data.dto.response.LoginResponse;
import com.home.bakery.data.entities.User;
import com.home.bakery.data.repositories.UserRepository;
import com.home.bakery.exceptions.ForbiddenException;
import com.home.bakery.exceptions.InValidAuthorizationException;
import com.home.bakery.exceptions.NotFoundException;
import com.home.bakery.exceptions.message.Message;
import com.home.bakery.services.authen.SecurityContextService;
import com.home.bakery.utils.JwtTokenUtil;

import java.util.Optional;

@Service
public class SecurityContextServiceImpl implements SecurityContextService {

    @Autowired
    private SecurityContext securityContext;
    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;
    private Message message;
    private PasswordEncoder passwordEncoder;

    @Override
    public void setSecurityContext(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new ForbiddenException("Invalid username in JWT.");
        }
        UserDetails userDetails = new CustomUserDetails(userOptional.get());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        securityContext.setAuthentication(usernamePasswordAuthenticationToken);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        return ((CustomUserDetails) principal).getUser();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(
                        () -> new NotFoundException(message.notFoundObjectMessage("User", loginRequest.getEmail())));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InValidAuthorizationException("Wrong user name or password");
        }

        return LoginResponse.builder()
                .accessToken(jwtTokenUtil.generateJwtToken(user, 1000))
                .refreshToken(jwtTokenUtil.generateJwtToken(user, 20000))
                .build();
    }
}