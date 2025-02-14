package com.sahadev.E_com.controllers;

import com.sahadev.E_com.dto.CartDto;
import com.sahadev.E_com.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/cart")
public class CartController {
    @Autowired private CartService cartService;

    @PostMapping ("/add")
    public ResponseEntity<CartDto> addProductToCart (@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.addProductToCart(userId,productId,quantity));
    }
}
