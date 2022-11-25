package com.example.ecommerce.dto.order;

import com.example.ecommerce.model.Order;

public class OrderDto {

    private Long id;
    private Long userId;

    public OrderDto() {
    }

    public OrderDto(Order order) {
        this.setId(order.getOrderId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
