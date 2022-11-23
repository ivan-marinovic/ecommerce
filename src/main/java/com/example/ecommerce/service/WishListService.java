package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.WishList;
import com.example.ecommerce.repository.WishListRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductService productService;
    public WishListService(WishListRepository wishListRepository, ProductService productService) {
        this.wishListRepository = wishListRepository;
        this.productService = productService;
    }

    public void createWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<ProductDto> getWishListForUser(User user) {
        final List<WishList> wishLists = wishListRepository.findByUser(user);
        List<ProductDto> productDtos = new ArrayList<>();
        for(WishList wishList : wishLists) {
            productDtos.add(productService.getProductDto(wishList.getProduct()));
        }
        return productDtos;
    }
}
