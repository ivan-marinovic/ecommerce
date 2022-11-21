package com.example.ecommerce.service;

import antlr.Token;
import com.example.ecommerce.model.AuthenticationToken;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.AuthTokenRepository;
import org.springframework.stereotype.Service;

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
}
