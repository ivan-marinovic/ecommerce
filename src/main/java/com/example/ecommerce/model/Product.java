package com.example.ecommerce.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @NotNull
    private String name;
    private Double price;
    @NotNull
    private String imageUrl;
    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<WishList> wishListList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Cart> carts;


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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
