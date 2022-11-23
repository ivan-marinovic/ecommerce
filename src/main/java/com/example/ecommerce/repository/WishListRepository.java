package com.example.ecommerce.repository;

import com.example.ecommerce.model.User;
import com.example.ecommerce.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findByUser(User user);
}
