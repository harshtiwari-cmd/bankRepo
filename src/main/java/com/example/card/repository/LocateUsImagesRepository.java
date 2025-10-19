package com.example.card.repository;

import com.example.card.constrants.entity.LocateUsImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocateUsImagesRepository extends JpaRepository<LocateUsImages, Integer> {

    LocateUsImages findByLocatorType(String locatorType);
}
