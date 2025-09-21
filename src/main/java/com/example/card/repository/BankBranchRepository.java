package com.example.card.repository;

import com.example.card.constrants.entity.BankBranchHarsh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranchHarsh, Long> {
}
