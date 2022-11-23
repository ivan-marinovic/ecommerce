package com.example.ecommerce.service;

import antlr.Token;
import com.example.ecommerce.exception.AuthenticationFailException;
import com.example.ecommerce.model.AuthenticationToken;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.AuthTokenRepository;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.Objects;

@Service
public class AuthenticationService {

    private final AuthTokenRepository authTokenRepository;
    public AuthenticationService(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        authTokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return authTokenRepository.findByUser(user);
    }

    public User getUser(String token) {
        final AuthenticationToken authenticationToken = authTokenRepository.findByToken(token);
        if(Objects.isNull(authenticationToken)) {
            return null;
        }
        return authenticationToken.getUser();
    }

    public void authenticate(String token) {
        if(Objects.isNull(token)) {
            throw new AuthenticationFailException("token not present");
        }
        if(Objects.isNull(getUser(token))) {
            throw new AuthenticationFailException("token not valid");
        }
    }
}
