package com.home.bakery.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.home.bakery.data.constans.UserRole;
import com.home.bakery.data.dto.request.UserDetailRequest;
import com.home.bakery.data.dto.request.UserRequest;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.services.user.UserService;
import com.home.bakery.services.userDetail.UserDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController()
@RequestMapping("api/users")
public class UserController {

        private UserService userService;
        private UserDetailService userDetailService;

        @Autowired
        public UserController(UserService userService, UserDetailService userDetailService) {
                this.userService = userService;
                this.userDetailService = userDetailService;
        }

        @Operation(summary = "Create new user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Create user successfully.", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequest.class)) }),
                        @ApiResponse(responseCode = "400", description = "Data not valid", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
        })
        @PostMapping()
        public ResponseEntity<Void> createNewUser(@Valid @RequestBody UserRequest userRequest) {
                userService.createUser(userRequest, UserRole.CUSTOMER);
                return ResponseEntity.status(HttpStatus.OK).build();
        }

        @Operation(summary = "Create new driver")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Create driver successfully.", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequest.class)) }),
                        @ApiResponse(responseCode = "400", description = "Data not valid", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
        })
        @PostMapping("/driver")
        @PreAuthorize("hasAuthority('OWNER')")
        public ResponseEntity<Void> createNewDriver(@Valid @RequestBody UserRequest userRequest) {
                userService.createUser(userRequest, UserRole.DRIVER);
                return ResponseEntity.status(HttpStatus.OK).build();
        }

        @Operation(summary = "Create user detail")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Create user detail successfully.", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequest.class)) }),
                        @ApiResponse(responseCode = "400", description = "Data not valid", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class)) })
        })
        @PostMapping("/detail")
        @PreAuthorize("hasAnyAuthority('OWNER','CUSTOMER')")
        public ResponseEntity<Void> createUserDetail(@Valid @RequestBody UserDetailRequest userDetailRequest) {
                userDetailService.createUserDetail(userDetailRequest);
                return ResponseEntity.status(HttpStatus.OK).build();
        }

}
