package com.sahadev.E_com.repos;

import com.sahadev.E_com.entities.Order;
import com.sahadev.E_com.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {
    public List<Order> findByUser(User user);
}
