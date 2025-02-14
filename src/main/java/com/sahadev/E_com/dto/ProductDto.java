package com.sahadev.E_com.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ProductDto {

    private Long id;

    @NotBlank (message = "Product name should not be blank")
    private String productName;

    @Size(min = 3,max = 500,message = "Product description should not be blank")
    private String productDescription;

    @NotNull (message = "Product price should not be blank")
    private Double productPrice;

    @NotNull (message = "Stock should not be null")
    private Long stock;

    @NotNull (message = "Category id should not be null")
    private Long categoryId;

}
