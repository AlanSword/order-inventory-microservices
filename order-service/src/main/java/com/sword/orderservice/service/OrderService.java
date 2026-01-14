package com.sword.orderservice.service;

import com.sword.orderservice.client.InventoryClient;
import com.sword.orderservice.dto.DeductRequest;
import com.sword.orderservice.model.Order;
import com.sword.orderservice.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepository;
    private final InventoryClient inventoryClient;

    public OrderService(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public Order createOrder(Order reqOrder) {
        try {
            inventoryClient.deduct(new DeductRequest(reqOrder.getProductId(), reqOrder.getQuantity()));
        } catch (feign.FeignException.Conflict ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Out of stock");
        }

        return orderRepository.save(reqOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
