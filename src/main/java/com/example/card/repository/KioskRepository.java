package com.example.card.repository;

import com.example.card.constrants.model.Kiosk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KioskRepository extends JpaRepository<Kiosk, Long> {

    Optional<Kiosk> findByKioskId(String kioskId);
}

