package com.sahadev.E_com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemDto {
    private Long id;
    private Long productId;
    private int quantity;
    private double totalPrice;
}
