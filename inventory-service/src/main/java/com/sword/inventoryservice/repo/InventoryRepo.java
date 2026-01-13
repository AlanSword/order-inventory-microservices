package com.sword.inventoryservice.repo;

import com.sword.inventoryservice.model.InventoryItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepo extends MongoRepository<InventoryItem, String> {

    InventoryItem findByProduct(String product);
}