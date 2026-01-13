package com.sword.inventoryservice.service;

import com.sword.inventoryservice.model.InventoryItem;
import com.sword.inventoryservice.repo.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepo inventoryRepository;

    public List<InventoryItem> getAllItems() {
        return inventoryRepository.findAll();
    }

    public InventoryItem getItemById(String id) {
        System.out.println(inventoryRepository.findById(id));
        return inventoryRepository.findById(id).orElse(null);
    }
    public InventoryItem getItemByProduct(String product) {
        return inventoryRepository.findByProduct(product);
    }


    public InventoryItem createItem(InventoryItem item) {
        return inventoryRepository.save(item);
    }

    public void deleteItem(String id) {
        inventoryRepository.deleteById(id);
    }
}
