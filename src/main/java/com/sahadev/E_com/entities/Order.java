package com.sahadev.E_com.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table (name = "order_table")
@Getter @Setter
public class Order {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String shippingAddress;

    private double totalPrice;

    @Enumerated (EnumType.STRING)
    private OrderStatus status;

    @OneToMany (mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

}
