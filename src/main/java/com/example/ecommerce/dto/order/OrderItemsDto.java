package com.example.ecommerce.dto.order;

import com.example.ecommerce.model.Order;

public class OrderItemsDto {

    private Double price;
    private Integer quantity;
    private Long orderId;
    private Integer productId;

    public OrderItemsDto() {}

    public OrderItemsDto(Double price, Integer quantity, Long orderId, Integer productId) {
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
