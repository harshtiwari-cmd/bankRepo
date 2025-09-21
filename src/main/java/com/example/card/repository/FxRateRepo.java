package com.example.card.repository;

import com.example.card.constrants.entity.FxRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FxRateRepo extends JpaRepository<FxRate,Integer> {
}
