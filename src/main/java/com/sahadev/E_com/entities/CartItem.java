package com.sahadev.E_com.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer itemQuantity;

    private Double totalPrice;

    @ManyToOne
    @JoinColumn (name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn (name = "product_id")
    @JsonIgnore
    private Product product;
}
