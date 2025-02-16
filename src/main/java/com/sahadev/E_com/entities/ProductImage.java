package com.sahadev.E_com.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ProductImage {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column (columnDefinition = "LONGBLOB")
    private byte [] data;

    @ManyToOne
    @JoinColumn (name = "product_id")
    @JsonIgnore
    private Product product;
}
