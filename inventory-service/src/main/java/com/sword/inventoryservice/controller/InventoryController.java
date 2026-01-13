package com.sword.inventoryservice.controller;

import com.sword.inventoryservice.model.InventoryItem;
import com.sword.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public InventoryItem createItem(@RequestBody InventoryItem item) {
        return inventoryService.createItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        inventoryService.deleteItem(id);
    }
}