package com.example.ecommerce.service.presentation;

import com.example.ecommerce.dto.user.LoginDto;
import com.example.ecommerce.dto.user.RegisterDto;
import com.example.ecommerce.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserPresentationService {

    public User convertRegisterDtoToModel(RegisterDto registerDto) {
        User user = new User();
        user.setUserId(registerDto.getUserId());
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(registerDto.getPassword());
        user.setRole(registerDto.getRole());

        return user;
    }

    public User convertLoginDtoToModel(LoginDto loginDto) {
        User user = new User();
        user.setEmail(loginDto.getEmail());
        user.setPassword(loginDto.getPassword());
        return user;
    }

}
