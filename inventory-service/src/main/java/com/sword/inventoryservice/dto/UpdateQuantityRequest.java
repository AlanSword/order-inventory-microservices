package com.sword.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateQuantityRequest(@NotBlank String id,@Min(0) int quantity) {}