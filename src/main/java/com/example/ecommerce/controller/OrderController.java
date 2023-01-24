package com.example.ecommerce.controller;

import com.example.ecommerce.dto.checkout.CheckoutItemDto;
import com.example.ecommerce.response.StripeResponse;
import com.example.ecommerce.exception.OrderNotFoundException;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

   private final OrderService orderService;
   private final UserService userService;
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList)
    throws StripeException {
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> placeOrder(@RequestHeader(name = "Authorization") String token, @RequestParam("sessionId") String sessionId) {
        User user = userService.getUserByToken(token);
        orderService.placeOrder(user, sessionId);
        return new ResponseEntity<>(new ApiResponse(1, "Order has been placed"),HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader(name = "Authorization") String token) {
        User user = userService.getUserByToken(token);
        List<Order> orderDtoList = orderService.listOrders(user);
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id)
     {
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }


}
