package com.example.card.service.impl;

import com.example.card.constrants.mapper.KioskMapper;
import com.example.card.constrants.model.Kiosk;

import com.example.card.dto.GeoLocationDTO;
import com.example.card.dto.KioskRequestDTO;
import com.example.card.dto.KioskResponseDTO;
import com.example.card.dto.LocationDTO;
import com.example.card.exceptions.BusinessException;
import com.example.card.repository.KioskRepository;
import com.example.card.services.impl.KioskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KioskServiceImplTest {

    @Mock
    private KioskRepository repository;

    @Mock
    private KioskMapper kioskMapper;

    @InjectMocks
    private KioskServiceImpl service;

    private KioskRequestDTO baseRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        baseRequest = new KioskRequestDTO();
        baseRequest.setKioskId("KS1002");
        baseRequest.setBranchId("Local");
        baseRequest.setName("Town Services");

        LocationDTO location = new LocationDTO();
        GeoLocationDTO geo = new GeoLocationDTO();
        geo.setLatitude(12.0f);
        geo.setLongitude(77.0f);
        location.setGeoLocation(geo);
        baseRequest.setLocation(location);
    }


    @Test
    void createKiosk_success() {
        Kiosk kiosk = new Kiosk();
        Kiosk saved = new Kiosk();
        KioskResponseDTO response = new KioskResponseDTO();
        response.setKioskId("KS1002");
        response.setBranchId("Local");
        response.setName("Town Services");

        when(kioskMapper.toEntity(baseRequest)).thenReturn(kiosk);
        when(repository.save(kiosk)).thenReturn(saved);
        when(kioskMapper.toDto(saved)).thenReturn(response);

        KioskResponseDTO result = service.createKiosk(baseRequest);

        assertEquals("KS1002", result.getKioskId());
        verify(repository).save(kiosk);
        verify(kioskMapper).toDto(saved);
    }


    @Test
    void createKiosk_withNullDto_throwsNPE() {
        assertThrows(NullPointerException.class, () -> service.createKiosk(null));
    }


    @Test
    void createKiosk_withNullKioskId_throwsBusinessException() {
        baseRequest.setKioskId(null);
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("KIOSK_ID_NULL", ex.getErrorCode());
    }

    @Test
    void createKiosk_withEmptyKioskId_throwsBusinessException() {
        baseRequest.setKioskId(" ");
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("KIOSK_ID_EMPTY", ex.getErrorCode());
    }

    @Test
    void createKiosk_withDuplicateKioskId_throwsBusinessException() {
        when(repository.existsByKioskId("KS1002")).thenReturn(true);
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("DUPLICATE_KIOSK_ID", ex.getErrorCode());
    }


    @Test
    void createKiosk_withNullBranchId_throwsBusinessException() {
        baseRequest.setBranchId(null);
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("BRANCH_ID_NULL", ex.getErrorCode());
    }

    @Test
    void createKiosk_withEmptyBranchId_throwsBusinessException() {
        baseRequest.setBranchId(" ");
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("BRANCH_ID_EMPTY", ex.getErrorCode());
    }


    @Test
    void createKiosk_withNullName_throwsBusinessException() {
        baseRequest.setName(null);
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("NAME_NULL", ex.getErrorCode());
    }

    @Test
    void createKiosk_withEmptyName_throwsBusinessException() {
        baseRequest.setName(" ");
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("NAME_EMPTY", ex.getErrorCode());
    }

    @Test
    void createKiosk_withDuplicateName_throwsBusinessException() {
        when(repository.existsByName("Town Services")).thenReturn(true);
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("DUPLICATE_NAME", ex.getErrorCode());
    }


    @Test
    void createKiosk_withNullLocation_throwsBusinessException() {
        baseRequest.setLocation(null);
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("LOCATION_NULL", ex.getErrorCode());
    }

    @Test
    void createKiosk_withNullGeoLocation_throwsBusinessException() {
        baseRequest.getLocation().setGeoLocation(null);
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("COORDINATES_NULL", ex.getErrorCode());
    }

    @Test
    void createKiosk_withNullLatitude_throwsBusinessException() {
        baseRequest.getLocation().getGeoLocation().setLatitude(null);
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("COORDINATES_NULL", ex.getErrorCode());
    }

    @Test
    void createKiosk_withNullLongitude_throwsBusinessException() {
        baseRequest.getLocation().getGeoLocation().setLongitude(null);
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("COORDINATES_NULL", ex.getErrorCode());
    }


    @Test
    void createKiosk_withInvalidLatitude_throwsBusinessException() {
        baseRequest.getLocation().getGeoLocation().setLatitude(200f);
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("INVALID_COORDINATES", ex.getErrorCode());
    }

    @Test
    void createKiosk_withInvalidLongitude_throwsBusinessException() {
        baseRequest.getLocation().getGeoLocation().setLongitude(200f);
        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("INVALID_COORDINATES", ex.getErrorCode());
    }


    @Test
    void createKiosk_withDuplicateCoordinates_throwsBusinessException() {
        when(repository.existsByLocationGeoLocationLatitudeAndLocationGeoLocationLongitude(12.0f, 77.0f))
                .thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.createKiosk(baseRequest));
        assertEquals("DUPLICATE_COORDINATES", ex.getErrorCode());
    }




    @Test
    void createKiosk_withBoundaryCoordinates_success() {
        baseRequest.getLocation().getGeoLocation().setLatitude(90f);
        baseRequest.getLocation().getGeoLocation().setLongitude(-180f);

        Kiosk kiosk = new Kiosk();
        Kiosk saved = new Kiosk();
        KioskResponseDTO response = new KioskResponseDTO();
        response.setKioskId("KS1002");

        when(kioskMapper.toEntity(baseRequest)).thenReturn(kiosk);
        when(repository.save(kiosk)).thenReturn(saved);
        when(kioskMapper.toDto(saved)).thenReturn(response);

        KioskResponseDTO result = service.createKiosk(baseRequest);
        assertEquals("KS1002", result.getKioskId());
    }
}
