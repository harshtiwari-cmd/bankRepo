package com.example.card.repository;

import com.example.card.constrants.model.Kiosk;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KioskRepository extends JpaRepository<Kiosk, Long> {
    boolean existsByKioskId(@NotBlank(message = "kiosk id should not be blank") String kioskId);

    boolean existsByName(@NotBlank(message = "name should not be blank") String name);

//    boolean existsByLocationGeoLocationLatitudeAndLocationGeoLocationLongitude(Float latitude, Float longitude);

}