package com.sword.inventoryservice.controller;

import com.sword.inventoryservice.dto.DeductRequest;
import com.sword.inventoryservice.dto.DeductResponse;
import com.sword.inventoryservice.dto.UpdateQuantityRequest;
import com.sword.inventoryservice.model.InventoryItem;
import com.sword.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<InventoryItem> getAllItems() {

        return inventoryService.getAllItems();
    }

    @GetMapping("/{id}")
    public InventoryItem getItemById(@PathVariable String id) {
        System.out.println("\""+id+"\"");
        return inventoryService.getItemById(id);

    }

    @GetMapping("/product/{product}")
    public InventoryItem getItemByProduct(@PathVariable String product) {
        return inventoryService.getItemByProduct(product);
    }

    @PostMapping("/admin")
    public InventoryItem createItem(@RequestBody InventoryItem item) {
        return inventoryService.createItem(item);
    }

    @DeleteMapping("/admin/{id}")
    public void deleteItem(@PathVariable String id) {
        inventoryService.deleteItem(id);
    }

    @PostMapping("/deduct")
    public DeductResponse deduct(@RequestBody DeductRequest request) {
        return inventoryService.deduct(request);
    }
    @PostMapping("/admin/addQuantity")
    public InventoryItem updateQuantity(
            @Valid @RequestBody UpdateQuantityRequest body
    ) {
       return inventoryService.updateQuantityById(body.id(), body.quantity());

    }

}