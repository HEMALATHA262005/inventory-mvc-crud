package com.example.inventory.service;

import com.example.inventory.model.AuditLog;
import com.example.inventory.model.InventoryItem;
import com.example.inventory.repository.AuditLogRepository;
import com.example.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryRepository repo;
    private final AuditLogRepository auditRepo;

    public InventoryService(InventoryRepository repo, AuditLogRepository auditRepo) {
        this.repo = repo;
        this.auditRepo = auditRepo;
    }

    public InventoryItem addItem(InventoryItem item) {
        // basic duplicate check by SKU
        repo.findBySku(item.getSku()).ifPresent(existing -> {
            throw new RuntimeException("SKU already exists: " + item.getSku());
        });
        InventoryItem saved = repo.save(item);
        saveAudit("ADD", "Added item id=" + saved.getId() + ", sku=" + saved.getSku());
        return saved;
    }

    public InventoryItem updateItem(Long id, InventoryItem updated) {
        InventoryItem existing = repo.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        existing.setName(updated.getName());
        existing.setSku(updated.getSku());
        existing.setCategory(updated.getCategory());
        existing.setQuantity(updated.getQuantity());
        existing.setSupplier(updated.getSupplier());
        existing.setPrice(updated.getPrice());
        existing.setLocation(updated.getLocation());
        InventoryItem saved = repo.save(existing);
        saveAudit("UPDATE", "Updated item id=" + saved.getId());
        return saved;
    }

    public void deleteItem(Long id) {
        InventoryItem existing = repo.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        repo.delete(existing);
        saveAudit("DELETE", "Deleted item id=" + id + ", sku=" + existing.getSku());
    }

    public List<InventoryItem> listAll() { return repo.findAll(); }
    public Optional<InventoryItem> getById(Long id) { return repo.findById(id); }

    private void saveAudit(String action, String details) {
        AuditLog a = new AuditLog();
        a.setAction(action);
        a.setDetails(details);
        auditRepo.save(a);
    }
}
