package com.msglearning.javabackend.repositories;

import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.to.PurchaseTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByUserId(Long userId);
}
