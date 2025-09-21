package com.example.card.repository;

import com.example.card.constrants.entity.BankDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankDetailsRepository extends JpaRepository<BankDetailsEntity,Long> {
   BankDetailsEntity findTopBy();
}
