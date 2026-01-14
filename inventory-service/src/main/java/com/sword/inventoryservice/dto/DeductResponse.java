package com.sword.inventoryservice.dto;


public record DeductResponse(
        String id,
        int deductedQty,
        int remainingQty
) {}