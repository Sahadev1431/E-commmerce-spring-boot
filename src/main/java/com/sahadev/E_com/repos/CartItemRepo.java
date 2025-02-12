package com.sahadev.E_com.repos;

import com.sahadev.E_com.entities.Cart;
import com.sahadev.E_com.entities.CartItem;
import com.sahadev.E_com.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
