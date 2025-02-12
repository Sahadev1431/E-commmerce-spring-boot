package com.sahadev.E_com.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemDto {

    private Long id;
    private Long productId;
    private Double totalPrice;
    private Integer itemQuantity;
}
