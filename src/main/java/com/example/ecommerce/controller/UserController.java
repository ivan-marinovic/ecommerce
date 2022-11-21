package com.example.ecommerce.controller;

import com.example.ecommerce.dto.user.LoginDto;
import com.example.ecommerce.dto.user.LoginResponseDto;
import com.example.ecommerce.dto.user.RegisterDto;
import com.example.ecommerce.dto.ResponseDto;
import com.example.ecommerce.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseDto register(@RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }
}
