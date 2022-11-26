package com.example.ecommerce.service;

import com.example.ecommerce.dto.user.LoginDto;
import com.example.ecommerce.dto.user.LoginResponseDto;
import com.example.ecommerce.dto.user.RegisterDto;
import com.example.ecommerce.dto.ResponseDto;
import com.example.ecommerce.dto.user.UserUpdateDto;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.exception.AuthenticationFailException;
import com.example.ecommerce.exception.CustomException;
import com.example.ecommerce.model.AuthenticationToken;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    public UserService(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDto register(RegisterDto registerDto) {
        if(Objects.nonNull(userRepository.findByEmail(registerDto.getEmail()))) {
            throw new CustomException("email is already taken");
        }

        String encryptedPassword = registerDto.getPassword();

        try {
             encryptedPassword = hashPassword(registerDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String userRole = String.valueOf(Role.customer);

        User user = new User(
                registerDto.getFirstName(),
                registerDto.getLastName(),
                registerDto.getEmail(),
                userRole,
                encryptedPassword
        );

        userRepository.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "user created successfully");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public LoginResponseDto login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if(Objects.isNull(user)) {
            throw new AuthenticationFailException("user does not exist");
        }

        try{
            if(!user.getPassword().equals(hashPassword(loginDto.getPassword()))) {
                throw new AuthenticationFailException("wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AuthenticationToken token = authenticationService.getToken(user);
        if(Objects.isNull(token)) {
            throw new CustomException("token does not exist");
        }

        return new LoginResponseDto("success", token.getToken());
    }

    public void updateUser(Long userId, UserUpdateDto userUpdateDto) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()) {
            throw new Exception("user does not exists");
        }
        User user = optionalUser.get();
        userUpdateDto.setFirstName(user.getFirstName());
        userUpdateDto.setLastName(user.getLastName());
        userUpdateDto.setRole(user.getRole());
        userRepository.save(user);
    }

}
