package com.sahadev.E_com.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class OrderDto {
    private Long id;
    private Long userId;
    private String shippingAddress;
    private double totalPrice;
    private LocalDateTime orderDate;
    private String status;
    private List<OrderItemDto> orderItems;
}
