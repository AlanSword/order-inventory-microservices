package com.sword.inventoryservice.service;

import com.sword.inventoryservice.dto.DeductRequest;
import com.sword.inventoryservice.dto.DeductResponse;
import com.sword.inventoryservice.model.InventoryItem;
import com.sword.inventoryservice.repo.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional
    public DeductResponse deduct(DeductRequest req) {
        InventoryItem item = inventoryRepository.findById(req.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (req.qty() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "qty must be > 0");
        }

        if (item.getQuantity() < req.qty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient stock");
        }

        item.setQuantity(item.getQuantity() - req.qty());
        inventoryRepository.save(item);

        return new DeductResponse(item.getId(), req.qty(), item.getQuantity());
    }
    @Transactional
    public InventoryItem updateQuantityById(String id, int addQty) {
        InventoryItem item = inventoryRepository.findById(id).orElse(null);
        int qty= item.getQuantity()+addQty;
        long updated = inventoryRepository.updateQuantityById(id, qty);
        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id);
        }
        return inventoryRepository.findById(id).orElse(null);
    }


}
