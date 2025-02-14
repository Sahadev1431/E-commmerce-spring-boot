package com.sahadev.E_com.controllers;

import com.sahadev.E_com.dto.CategoryDto;
import com.sahadev.E_com.entities.Category;
import com.sahadev.E_com.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/category")
public class CategoryController {
    @Autowired private CategoryService categoryService;

    @PostMapping ("/add")
    public ResponseEntity<Category> addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        System.out.println("req in controller");
        Category savedCategory = categoryService.addCategory (categoryDto);
        return ResponseEntity.ok(savedCategory);
    }
}
