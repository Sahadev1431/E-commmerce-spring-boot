package com.sahadev.E_com.controllers;

import com.sahadev.E_com.dto.OrderDto;
import com.sahadev.E_com.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/order")
public class OrderController {
    @Autowired private OrderService orderService;

    @PostMapping ("/place/{userId}")
    public ResponseEntity<OrderDto> placeOrder (@PathVariable Long userId, @RequestParam String shippingAddress) {
        return ResponseEntity.ok(orderService.placeOrder(userId,shippingAddress));
    }
}
