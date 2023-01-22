package com.example.ecommerce.controller;

import com.example.ecommerce.dto.cart.AddToCartDto;
import com.example.ecommerce.dto.cart.CartDto;
import com.example.ecommerce.model.User;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestHeader(name = "Authorization") String token) {
        User user = userService.getUserByToken(token);
        cartService.addToCart(addToCartDto, user);
        return new ResponseEntity<>(new ApiResponse(1, "Added to cart"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getCartItems(@RequestHeader(name = "Authorization") String token) {
        User user = userService.getUserByToken(token);
        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Long itemId, @RequestParam("token") String token) {
        User user = userService.getUserByToken(token);
        cartService.deleteCartItem(itemId, user);
        return new ResponseEntity<>(new ApiResponse(1, "Item has removed from cart"), HttpStatus.OK);
    }
}
