package com.sahadev.E_com.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductImageDto {

    private Long id;

    private byte [] data;

}
