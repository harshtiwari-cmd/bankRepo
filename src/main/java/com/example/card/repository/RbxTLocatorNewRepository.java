package com.example.card.repository;

import com.example.card.constrants.entity.RbxTLocatorNewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RbxTLocatorNewRepository extends JpaRepository<RbxTLocatorNewEntity, String> {

    List<RbxTLocatorNewEntity> findByLocatorTypeIgnoreCase(String locatorType);

    List<RbxTLocatorNewEntity> findByLocatorTypeIgnoreCaseAndCityIgnoreCase(String locatorType, String city);

    List<RbxTLocatorNewEntity> findByLocatorTypeIgnoreCaseAndCodeIgnoreCase(String locatorType, String code);

    @Query("select e from RbxTLocatorNewEntity e where (:locatorType is null or lower(e.locatorType)=lower(:locatorType)) and (:city is null or lower(e.city)=lower(:city)) and (:code is null or lower(e.code)=lower(:code))")
    List<RbxTLocatorNewEntity> search(@Param("locatorType") String locatorType,
                                      @Param("city") String city,
                                      @Param("code") String code);
}


