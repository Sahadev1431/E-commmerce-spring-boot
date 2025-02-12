//package com.sahadev.E_com.services;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sahadev.E_com.DTO.CartDto;
//import com.sahadev.E_com.entities.Cart;
//import com.sahadev.E_com.entities.CartItem;
//import com.sahadev.E_com.entities.Product;
//import com.sahadev.E_com.entities.User;
//import com.sahadev.E_com.repos.CartItemRepo;
//import com.sahadev.E_com.repos.CartRepo;
//import com.sahadev.E_com.repos.ProductRepo;
//import com.sahadev.E_com.repos.UserRepo;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.Banner;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CartService {
//    @Autowired private CartRepo cartRepo;
//    @Autowired private UserRepo userRepo;
//    @Autowired private ProductRepo productRepo;
//    @Autowired private CartItemRepo cartItemRepo;
//    @Autowired private ObjectMapper objectMapper;
//    @Autowired private ModelMapper modelMapper;
//
//    public CartDto addProductToCart(Long userId, Long productId, Integer quantity) {
//        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id : " + userId));
//        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id : " + productId));
//
//        Cart cart = user.getCart();
//        if (cart == null) {
//            cart = new Cart();
//            cart.setUser(user);
//            user.setCart(cart);
//        }
//
//        CartItem cartItem = cart.getCartItems().stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst()
//                .orElse(null);
//
//        if (cartItem == null) {
//            cartItem = new CartItem();
//            cartItem.setCart(cart);
//            cartItem.setProduct(product);
//            cartItem.setItemQuantity(quantity);
//        } else {
//            cartItem.setItemQuantity(cartItem.getItemQuantity() + quantity);
//        }
//
//        cartItem.setTotalPrice(cartItem.getItemQuantity() * product.getProductPrice());
//
//        if (!cart.getCartItems().contains(cartItem)) {
//            cart.getCartItems().add(cartItem);
//        }
//
//        cartRepo.save(cart);
//        cartItemRepo.save(cartItem);
//
////        return modelMapper.map(cart,CartDto.class);
//        return objectMapper.convertValue(cart,CartDto.class);
//    }
//}



package com.sahadev.E_com.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahadev.E_com.DTO.CartDto;
import com.sahadev.E_com.DTO.CartItemDto;
import com.sahadev.E_com.entities.Cart;
import com.sahadev.E_com.entities.CartItem;
import com.sahadev.E_com.entities.Product;
import com.sahadev.E_com.entities.User;
import com.sahadev.E_com.repos.CartRepo;
import com.sahadev.E_com.repos.ProductRepo;
import com.sahadev.E_com.repos.UserRepo;
import com.sahadev.E_com.repos.CartItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired private CartRepo cartRepo;
    @Autowired private UserRepo userRepo;
    @Autowired private ProductRepo productRepo;
    @Autowired private CartItemRepo cartItemRepo;
    @Autowired private ObjectMapper objectMapper;

    public CartDto addProductToCart(Long userId, Long productId, Integer quantity) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id : " + userId));
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id : " + productId));

        Cart cart = cartRepo.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepo.save(newCart);
        });

        CartItem cartItem = cartItemRepo.findByCartAndProduct(cart, product).orElse(null);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setUser(user);
            cartItem.setItemQuantity(quantity);
        } else {
            cartItem.setItemQuantity(cartItem.getItemQuantity() + quantity);
        }

        cartItem.setTotalPrice(cartItem.getItemQuantity() * product.getProductPrice());
        cartItemRepo.save(cartItem);

        if (!cart.getCartItems().contains(cartItem)) {
            cart.getCartItems().add(cartItem);
        }

        cartRepo.save(cart);

        return convertToDto(cart);
    }

    private CartDto convertToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUserId(cart.getUser().getId());
        cartDto.setTotalPrice(cart.getCartItems().stream().mapToDouble(CartItem::getTotalPrice).sum());

        List<CartItemDto> cartItemDtos = cart.getCartItems().stream().map(item -> {
            CartItemDto dto = new CartItemDto();
            dto.setId(item.getId());
            dto.setProductId(item.getProduct().getId());
            dto.setItemQuantity(item.getItemQuantity());
            dto.setTotalPrice(item.getTotalPrice());
            return dto;
        }).collect(Collectors.toList());

        cartDto.setCartItemDtos(cartItemDtos);
        return cartDto;
    }
}






