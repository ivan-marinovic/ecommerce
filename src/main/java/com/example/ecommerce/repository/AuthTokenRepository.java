package com.example.ecommerce.repository;

import com.example.ecommerce.model.AuthenticationToken;
import com.example.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findByUser(User user);
}
