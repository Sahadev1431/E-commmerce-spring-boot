package com.sahadev.E_com.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    @NotBlank (message = "Category name should not be blank")
    private String categoryName;
}
