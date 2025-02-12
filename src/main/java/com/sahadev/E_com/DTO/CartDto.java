package com.sahadev.E_com.DTO;

import com.sahadev.E_com.entities.CartItem;
import jakarta.persistence.*;
import lombok.Data;
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
