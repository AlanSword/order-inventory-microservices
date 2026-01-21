package com.sword.orderservice.controller;


import com.sword.orderservice.dto.OrderItemDTO;
import com.sword.orderservice.model.Order;
import com.sword.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orderList")
    public List<Order> getAllOrders(@RequestHeader("X-Authenticated-User") String username) {
        return orderService.getAllOrders(username);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id, @RequestHeader("X-Authenticated-User") String username) {
        return orderService.getOrderById(id,username);
    }

    @PostMapping("/place")
    public Order createOrder(@RequestBody OrderItemDTO orderItemDTO, @RequestHeader("X-Authenticated-User") String username) {
        return orderService.createOrder(orderItemDTO,username);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id, @RequestHeader("X-Authenticated-User") String username) {
        orderService.deleteOrder(id,username);
    }

    @GetMapping("/admin/allorders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/admin/{username}")
    public List<Order> getOrders(@PathVariable String username) {
        return orderService.getAllOrders(username);
    }

    @GetMapping("/admin/order/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/admin/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}