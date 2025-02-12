package com.sahadev.E_com.DTO;

import com.sahadev.E_com.entities.OrderItem;
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
