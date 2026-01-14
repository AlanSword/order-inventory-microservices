package com.sword.orderservice.client;


import com.sword.orderservice.dto.DeductRequest;
import com.sword.orderservice.dto.DeductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "inventory-service",
        path = "/api/inventory"
)
public interface InventoryClient {

    @PostMapping("/deduct")
    DeductResponse deduct(@RequestBody DeductRequest request);
}
