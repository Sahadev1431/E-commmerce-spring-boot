package com.sahadev.E_com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahadev.E_com.dto.ProductDto;
import com.sahadev.E_com.entities.Product;
import com.sahadev.E_com.entities.ProductImage;
import com.sahadev.E_com.repos.ProductImageRepo;
import com.sahadev.E_com.repos.ProductRepo;
import com.sahadev.E_com.services.CategoryService;
import com.sahadev.E_com.services.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private ProductRepo productRepo;
    @Autowired private CategoryService categoryService;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ProductImageRepo productImageRepo;

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

    @PostMapping ("/upload/{productId}")
    public ResponseEntity<String> uploadImage(@PathVariable Long productId, @RequestParam ("files") MultipartFile[] multipartFiles) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        try {
            List<ProductImage> imageList = new ArrayList<>();
            for (MultipartFile file : multipartFiles) {
                ProductImage productImage = new ProductImage();
                productImage.setProduct(product);
                productImage.setData(file.getBytes());

                imageList.add(productImage);
            }

            productImageRepo.saveAll(imageList);
            return ResponseEntity.ok("images uploaded successfully for product : " + product.getProductName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload images!");
        }
    }

    @GetMapping ("/image/{productId}")
    public ResponseEntity<byte[]> getProductImages(@PathVariable Long productId) {
        List<ProductImage> imageList = productImageRepo.findByProductId(productId);

        if (imageList.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        byte[] imageData = imageList.get(0).getData();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
    }

    @GetMapping ("/images/{productId}")
    public ResponseEntity<List<String>> getProductImageUrls(@PathVariable Long productId) {
        List<ProductImage> imageList = productImageRepo.findByProductId(productId);

        if (imageList.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        List<String> imageUrls = new ArrayList<>();
        for (ProductImage image : imageList) {
            String imageUrl = "http://localhost:8080/api/product/image/" + image.getId();
            imageUrls.add(imageUrl);
        }
        return ResponseEntity.ok(imageUrls);
    }
}
