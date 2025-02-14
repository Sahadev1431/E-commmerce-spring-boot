package com.sahadev.E_com.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahadev.E_com.dto.OrderDto;
import com.sahadev.E_com.dto.OrderItemDto;
import com.sahadev.E_com.entities.*;
import com.sahadev.E_com.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired OrderRepo orderRepo;
    @Autowired UserRepo userRepo;
    @Autowired CartRepo cartRepo;
    @Autowired ProductRepo productRepo;
    @Autowired CartItemRepo cartItemRepo;
    @Autowired ObjectMapper objectMapper;
    @Autowired private OrderItemRepo orderItemRepo;

    public OrderDto placeOrder(Long userID, String shippingAddress) {
        User user = userRepo.findById(userID).orElseThrow(() -> new RuntimeException("User not found with id : " + userID));

        Cart cart = cartRepo.findByUser(user).orElse(null);

        if (cart == null || cart.getCartItems().isEmpty()) throw new RuntimeException("Cart is empty for user : " + user.getFirstName());

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setStatus(OrderStatus.PLACED);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();

            if (product.getStock() < cartItem.getItemQuantity()) throw new RuntimeException("Sorry to say but stock is not available for product : " + product.getProductName());
            product.setStock(product.getStock() - cartItem.getItemQuantity());
            productRepo.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getItemQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            orderItems.add(orderItem);
            totalPrice += orderItem.getTotalPrice();
        }

        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

//        orderRepo.save(order);
        try {
            orderRepo.save(order);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while saving order: " + e.getMessage());
            throw new RuntimeException("Order saving failed: " + e.getMessage());
        }

        orderItemRepo.saveAll(orderItems);

        cartItemRepo.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepo.save(cart);

        return convertToDto(order);
    }

    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus().name());
        dto.setShippingAddress(order.getShippingAddress());

        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream().map(item -> {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setTotalPrice(item.getTotalPrice());
            return itemDto;
        }).collect(Collectors.toList());

        dto.setOrderItems(orderItemDtos);
        return dto;
    }

}
