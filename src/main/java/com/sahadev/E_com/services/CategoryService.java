package com.sahadev.E_com.services;

import com.sahadev.E_com.dto.CategoryDto;
import com.sahadev.E_com.entities.Category;
import com.sahadev.E_com.repos.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired private CategoryRepo categoryRepo;
    @Autowired private ModelMapper modelMapper;

    public Category addCategory(CategoryDto categoryDto) {
        System.out.println("req in service");
        Category category = modelMapper.map(categoryDto,Category.class);
        System.out.println("req after model mapper");
        return categoryRepo.save(category);
    }

    public Category findById(Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found with id : " + id));
    }
}
