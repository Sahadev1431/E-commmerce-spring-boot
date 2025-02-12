package com.sahadev.E_com.repos;

import com.sahadev.E_com.entities.Cart;
import com.sahadev.E_com.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUser(User user);
}
