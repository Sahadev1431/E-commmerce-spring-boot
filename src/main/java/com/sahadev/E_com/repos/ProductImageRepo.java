package com.sahadev.E_com.repos;

import com.sahadev.E_com.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepo extends JpaRepository<ProductImage,Long> {
    List<ProductImage> findByProductId (Long productId);
}
