package com.sahadev.E_com.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CartDto {
    private Long id;

    private Long userId;

    private List<CartItemDto> cartItemDtos;

    private Double totalPrice;
}
