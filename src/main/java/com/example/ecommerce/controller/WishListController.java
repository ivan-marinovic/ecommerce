package com.example.ecommerce.controller;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.WishList;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/wishlist")
public class WishListController {

   private final WishListService wishListService;
   private final UserService userService;
    public WishListController(WishListService wishListService, UserService userService) {
        this.wishListService = wishListService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addToWishlist(@RequestBody Product product, @RequestHeader(name = "Authorization") String token) {
        User user = userService.getUserByToken(token);
        WishList wishList = new WishList(user, product);
        wishListService.createWishList(wishList);
        return new ResponseEntity<>(new ApiResponse(1, "added to wishlist"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getWishList(@RequestHeader(name = "Authorization") String token) {
        User user = userService.getUserByToken(token);
        List<ProductDto> productsDto = wishListService.getWishListForUser(user);
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

}
