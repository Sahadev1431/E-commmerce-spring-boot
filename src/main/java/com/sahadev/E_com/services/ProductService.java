package com.sahadev.E_com.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahadev.E_com.dto.ProductDto;
import com.sahadev.E_com.dto.ProductImageDto;
import com.sahadev.E_com.entities.Category;
import com.sahadev.E_com.entities.OrderItem;
import com.sahadev.E_com.entities.Product;
import com.sahadev.E_com.repos.OrderItemRepo;
import com.sahadev.E_com.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired private ProductRepo productRepo;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private CategoryService categoryService;
    @Autowired private OrderItemRepo orderItemRepo;

//    @Transactional
    public ProductDto addProduct(ProductDto productDto,Long categoryId) {


        Product product = objectMapper.convertValue(productDto,Product.class);
        product.setCategory(categoryService.findById(categoryId));
        Product savedProduct =  productRepo.save(product);
        ProductDto returnDto = objectMapper.convertValue(savedProduct,ProductDto.class);
        returnDto.setCategoryId(categoryId);

        return returnDto;
    }

    public ProductDto findById(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        ProductDto productDto =  objectMapper.convertValue(product,ProductDto.class);
        productDto.setCategoryId(product.getCategory().getId());
        if (product.getProductImages() != null) {
            List<ProductImageDto> productImageDtos = product.getProductImages().stream()
                    .map(image -> {
                        ProductImageDto imageDto = objectMapper.convertValue(image, ProductImageDto.class);
                        imageDto.setProductId(image.getProduct().getId()); // Set productId explicitly
                        return imageDto;
                    })
                    .collect(Collectors.toList());
            productDto.setProductImages(productImageDtos);
        }
        return productDto;
    }

    public ProductDto updateProductById(long id,ProductDto productDto) {
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
        Product updatedProduct =  productRepo.save(existingProduct);
        return objectMapper.convertValue(updatedProduct,ProductDto.class);
    }


    public void deleteProductById(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("product not found with id : " + id));
        List<OrderItem> orderItems = orderItemRepo.findByProduct(product);
        orderItemRepo.deleteAll(orderItems);
        productRepo.delete(product);
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
