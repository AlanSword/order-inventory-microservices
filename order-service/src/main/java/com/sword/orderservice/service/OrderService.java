package com.sword.orderservice.service;

import com.sword.orderservice.client.InventoryClient;
import com.sword.orderservice.dto.DeductRequest;
import com.sword.orderservice.dto.OrderItemDTO;
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
    public List<Order> getAllOrders(String user) {
        return orderRepository.findAllByUsername(user);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order getOrderById(Long id,String user) {
        return orderRepository.findByIdAndUsername(id,user).orElse(null);
    }

    @Transactional
    public Order createOrder(OrderItemDTO reqOrder, String username) {
        try {
            inventoryClient.deduct(new DeductRequest(reqOrder.getProductId(), reqOrder.getQuantity()));
        } catch (feign.FeignException.Conflict ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Out of stock");
        }
        Order order = new Order();
        order.setProduct(reqOrder.getProduct());
        order.setProductId(reqOrder.getProductId());
        order.setQuantity(reqOrder.getQuantity());
        order.setPrice(reqOrder.getPrice());
        order.setUsername(username);

        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id,String user) {

        orderRepository.deleteByIdAndUsername(id,user);
    }
    public void deleteOrder(Long id) {

         orderRepository.deleteById(id);
    }


}
