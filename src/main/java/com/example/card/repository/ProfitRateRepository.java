package com.example.card.repository;

import com.example.card.constrants.entity.ProfitRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitRateRepository extends JpaRepository<ProfitRate, Long> {
}
