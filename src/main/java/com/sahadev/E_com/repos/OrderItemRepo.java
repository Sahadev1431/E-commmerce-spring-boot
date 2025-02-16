package com.sahadev.E_com.repos;

import com.sahadev.E_com.entities.OrderItem;
import com.sahadev.E_com.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
    public List<OrderItem> findByProduct(Product product);
}
