package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.PurchaseConverter;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.repositories.PurchaseRepository;
import com.msglearning.javabackend.repositories.UserRepository;
import com.msglearning.javabackend.to.PurchaseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, GameRepository gameRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    // Gets purchases by user ID
    public ResponseEntity<List<PurchaseTO>> getPurchasesByUser(Long userId) {
        List<Purchase> purchases = purchaseRepository.findByUserId(userId);
        if (purchases.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        } else {
            List<PurchaseTO> purchaseTOs = purchases.stream()
                    .map(PurchaseConverter::toPurchaseTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(purchaseTOs);
        }
    }

    public ResponseEntity<List<PurchaseTO>> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        if (purchases.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        } else {
            List<PurchaseTO> purchaseTOs = purchases.stream()
                    .map(PurchaseConverter::toPurchaseTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(purchaseTOs);
        }
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
    }
