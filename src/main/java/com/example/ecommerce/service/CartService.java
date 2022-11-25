package com.example.ecommerce.service;

import com.example.ecommerce.dto.cart.AddToCartDto;
import com.example.ecommerce.dto.cart.CartDto;
import com.example.ecommerce.dto.cart.CartItemDto;
import com.example.ecommerce.exception.CustomException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    private final ProductService productService;
    private final CartRepository cartRepository;

    public CartService(ProductService productService, CartRepository cartRepository) {
        this.productService = productService;
        this.cartRepository = cartRepository;
    }
    public void addToCart(AddToCartDto addToCartDto, User user) {
        Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUser(user);

        List<CartItemDto> cartItems = new ArrayList<>();
        double totalAmount = 0;
        for(Cart cart : cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalAmount += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalAmount(totalAmount);
        cartDto.setCartItems(cartItems);
        return cartDto;
    }

    public void deleteCartItem(Long cartItemId, User user) {
        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if(optionalCart.isEmpty()) {
            throw new CustomException("cart item id is invalid: " + cartItemId);
        }

        Cart cart = optionalCart.get();

        if(cart.getUser() != user) {
            throw new CustomException("cart item does not belong to user: " + cartItemId);
        }

        cartRepository.delete(cart);
    }

    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }
}
