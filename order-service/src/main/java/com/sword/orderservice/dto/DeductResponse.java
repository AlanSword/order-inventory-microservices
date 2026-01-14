package com.sword.orderservice.dto;


public record DeductResponse(
        String id,
        int deductedQty,
        int remainingQty
) {}