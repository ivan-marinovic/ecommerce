package com.example.ecommerce.dto.product;

import com.example.ecommerce.model.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class ProductDto {

    private Long productId;
    private @NotNull String name;

    @NotNull
    @Positive
    private double price;
    private @NotNull String imageUrl;
    private @NotNull String description;
    private @NotNull Long categoryId;
    @NotNull
    @PositiveOrZero
    private Integer quantity;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.setProductId(product.getProductId());
        this.setName(product.getName());
        this.setImageUrl(product.getImageUrl());
        this.setDescription(product.getDescription());
        this.setPrice(product.getPrice());
        this.setCategoryId(product.getCategory().getCategoryId());
    }

    public ProductDto(@NotNull String name, @NotNull String imageURL, @NotNull @Min(value = 0) double price, @NotNull String description, @NotNull @Min(value = 0) Integer quantity, @NotNull Long categoryId) {
        this.name = name;
        this.imageUrl = imageURL;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
