package com.example.ecommerce.controller;

import com.example.ecommerce.common.ApiResponse;
import com.example.ecommerce.dto.user.LoginDto;
import com.example.ecommerce.dto.user.LoginResponseDto;
import com.example.ecommerce.dto.user.RegisterDto;
import com.example.ecommerce.dto.ResponseDto;
import com.example.ecommerce.dto.user.UserUpdateDto;
import com.example.ecommerce.service.AuthenticationService;
import com.example.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseDto register(@RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateDto userUpdateDto) throws Exception {
        userService.updateUser(userId, userUpdateDto);
        return new ResponseEntity<>(new ApiResponse(true, "user has been updated"), HttpStatus.OK);
    }
}
