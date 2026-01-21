package com.sword.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private String product;
    private String productId;
    private int quantity;
    private double price;
}