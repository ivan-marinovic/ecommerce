package com.example.ecommerce.service;

import com.example.ecommerce.dto.cart.CartDto;
import com.example.ecommerce.dto.cart.CartItemDto;
import com.example.ecommerce.exception.OrderNotFoundException;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final ShipmentService shipmentService;
    public OrderService(CartService cartService, OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductService productService, ShipmentService shipmentService) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
        this.shipmentService = shipmentService;
    }

    public void placeOrder(User user) {
        CartDto cartDto = cartService.listCartItems(user);
        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();

        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
        newOrder.setUser(user);
        newOrder.setTotalAmount(cartDto.getTotalAmount());
        newOrder.setAddress(user.getAddress());
        orderRepository.save(newOrder);
        shipmentService.createShipment(newOrder);


        for (CartItemDto cartItemDto : cartItemDtoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedDate(new Date());
            orderItem.setPrice(cartItemDto.getProduct().getPrice());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());
            productService.setProductQuantity(orderItem.getProduct().getProductId(), cartItemDto.getQuantity());
            orderItem.setOrder(newOrder);
            orderItemRepository.save(orderItem);
        }

        cartService.deleteUserCartItems(user);
    }

    public List<Order> listOrders(User user) {
        return orderRepository.findAllByUser(user);
    }

    public Order getOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        throw new OrderNotFoundException("Order not found");
    }
}
