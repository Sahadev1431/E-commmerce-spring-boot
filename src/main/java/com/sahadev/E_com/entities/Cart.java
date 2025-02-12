package com.sahadev.E_com.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Cart {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn (name = "user_id",referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @OneToMany (mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<CartItem> cartItems = new ArrayList<>();
}
