package com.example.ecommerce.dto.cart;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;

public class CartItemDto {
    private Long id;
    private Integer quantity;
    private Product product;

    public CartItemDto() {
    }

    public CartItemDto(Cart cart) {
        this.id = cart.getCartId();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
