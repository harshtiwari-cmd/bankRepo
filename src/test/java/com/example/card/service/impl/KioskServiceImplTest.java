package com.example.card.service.impl;

import com.example.card.constrants.mapper.KioskMapper;
import com.example.card.constrants.model.Kiosk;
import com.example.card.dto.KioskRequestDTO;
import com.example.card.dto.KioskResponseDTO;
import com.example.card.repository.KioskRepository;
import com.example.card.services.impl.KioskServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class KioskServiceImplTest {

    @Mock
    private KioskRepository repository;

    @Mock
    private KioskMapper kioskMapper;

    @InjectMocks
    private KioskServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void kioskSaveTest() {

        KioskRequestDTO requestDTO = new KioskRequestDTO();
        requestDTO.setKioskId("KS1002");
        requestDTO.setBranchId("Local");
        requestDTO.setName("town services");

        Kiosk kiosk = new Kiosk();

        KioskResponseDTO responseDTO = new KioskResponseDTO();
        responseDTO.setKioskId("KS1002");
        responseDTO.setBranchId("Local");
        responseDTO.setName("town services");

        Kiosk savedKiosik = new Kiosk();

        Mockito.when(kioskMapper.toEntity(requestDTO)).thenReturn(kiosk);
        Mockito.when(repository.save(kiosk)).thenReturn(savedKiosik);
        Mockito.when(kioskMapper.toDto(savedKiosik)).thenReturn(responseDTO);

        KioskResponseDTO result = service.createKiosk(requestDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDTO, result);
        Assertions.assertEquals(responseDTO.getKioskId(), result.getKioskId());
        Mockito.verify(kioskMapper).toDto(savedKiosik);
        Mockito.verify(repository).save(kiosk);

    }

    @Test
    public void createKiosk_shouldThrow_NullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            service.createKiosk(null);
        });
    }


}
