package com.sword.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record DeductRequest(
        @NotBlank String id,
        @Min(1) int qty
) {}