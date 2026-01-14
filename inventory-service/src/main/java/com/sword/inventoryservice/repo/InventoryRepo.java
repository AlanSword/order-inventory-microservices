package com.sword.inventoryservice.repo;

import com.sword.inventoryservice.model.InventoryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface InventoryRepo extends MongoRepository<InventoryItem, String> {

    InventoryItem findByProduct(String product);

    @Query("{ '_id': ?0 }")
    @Update("{ '$set': { 'quantity': ?1 } }")
    long updateQuantityById(String id, int quantity);
}