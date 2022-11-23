package com.example.ecommerce.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wishlist")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public WishList() {
    }

    public WishList(Long wishlistId, User user, Date createdDate, Product product) {
        this.wishlistId = wishlistId;
        this.user = user;
        this.createdDate = createdDate;
        this.product = product;
    }

    public WishList(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
