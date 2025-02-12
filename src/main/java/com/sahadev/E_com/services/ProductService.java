package com.sahadev.E_com.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahadev.E_com.DTO.ProductDto;
import com.sahadev.E_com.entities.Category;
import com.sahadev.E_com.entities.Product;
import com.sahadev.E_com.repos.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired private ProductRepo productRepo;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private CategoryService categoryService;

//    @Transactional
    public Product addProduct(ProductDto productDto,Long categoryId) {

        Category category = categoryService.findById(categoryId);
        Product product = objectMapper.convertValue(productDto,Product.class);
        product.setCategory(category);
        return productRepo.save(product);
    }

    public Product findById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found with id : " + id));
    }

    public Product updateProductById(long id,ProductDto productDto) {
        Product existingProduct = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id : " + id));

        Map<String,Object> updates = objectMapper.convertValue(productDto, Map.class);
        updates.forEach((key,value) -> {
            if (value != null) {
                if (key.equals("categoryId")) {
                    Long categoryId = ((Number) value).longValue();
                    Category category = categoryService.findById(categoryId);
                    existingProduct.setCategory(category);
                } else {
                    Field field = ReflectionUtils.findField(Product.class,key);
                    if (field != null) {
                        field.setAccessible(true);
                        ReflectionUtils.setField(field,existingProduct,value);
                    }
                }
            }
        });
        return productRepo.save(existingProduct);
    }


    public void deleteProductById(Long id) {
        productRepo.deleteById(id);
    }


    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream()
                .map(product -> {
                   ProductDto productDto = objectMapper.convertValue(product,ProductDto.class);
                   productDto.setCategoryId(product.getCategory().getId());
                   return productDto;
                }).collect(Collectors.toList());
    }
}
