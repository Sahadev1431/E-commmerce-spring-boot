package com.sahadev.E_com.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Getter @Setter
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;

    private String productName;

    private String productDescription;

    private Double productPrice;

    private Long stock;

    @ManyToOne
    @JoinColumn (name = "category_id")
    @JsonIgnore
    private Category category;


    @CreationTimestamp
    private LocalDateTime created_at;
}
