package com.sahadev.E_com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahadev.E_com.DTO.ProductDto;
import com.sahadev.E_com.entities.Category;
import com.sahadev.E_com.entities.Product;
import com.sahadev.E_com.services.CategoryService;
import com.sahadev.E_com.services.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ObjectMapper objectMapper;


    @GetMapping ("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @PostMapping
    public ResponseEntity<Product> addProduct (@RequestBody @Valid ProductDto productDto) {
        Long categoryId = productDto.getCategoryId();
        Product savedProduct = productService.addProduct(productDto,categoryId);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Product> updateProductById (@PathVariable Long id,@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProductById(id,productDto));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("product deleted successfully");
    }
}
