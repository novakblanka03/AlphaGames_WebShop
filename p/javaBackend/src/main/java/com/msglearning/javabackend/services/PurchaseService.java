package com.msglearning.javabackend.services;

import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    // Gets purchases by user ID
    public List<Purchase> getPurchasesByUser(Long userId) {
        return purchaseRepository.findByUserId(userId);
    }

    public ResponseEntity<Purchase> getPurchaseById(Long id) {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);
        return optionalPurchase.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Purchase> savePurchase(Purchase purchase) {
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPurchase);
    }

    public ResponseEntity<Purchase> updatePurchase(Long id, Purchase purchase) {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);
        if (optionalPurchase.isPresent()) {
            Purchase existingPurchase = optionalPurchase.get();
            // Update purchaseDate
            existingPurchase.setPurchaseDate(purchase.getPurchaseDate());
            Purchase updatedPurchase = purchaseRepository.save(existingPurchase);
            return ResponseEntity.ok(updatedPurchase);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

}
