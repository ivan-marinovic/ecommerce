package com.example.ecommerce.controller;

import com.example.ecommerce.dto.user.RegisterDto;
import com.example.ecommerce.model.User;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.config.JwtService;
import com.example.ecommerce.dto.user.LoginDto;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.service.presentation.UserPresentationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserPresentationService userPresentationService;
    public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, UserPresentationService userPresentationService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userPresentationService = userPresentationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterDto registerDto) {
        User user = userPresentationService.convertRegisterDtoToModel(registerDto);
        userService.register(user);
        return new ResponseEntity<>(new ApiResponse(1, "user has been created"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        User user = userPresentationService.convertLoginDtoToModel(loginDto);
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
