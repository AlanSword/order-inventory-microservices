package com.sword.orderservice.repo;


import com.sword.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findAllByUsername(String username);
    Optional<Order> findByIdAndUsername(Long id, String username);
    void deleteByIdAndUsername(Long id, String username);
}