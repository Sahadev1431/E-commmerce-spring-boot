package com.sahadev.E_com.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDto {
    @NotBlank (message = "Category name should not be blank")
    private String categoryName;
}
