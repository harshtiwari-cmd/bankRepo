package com.example.card.service.impl;

import com.example.card.constrants.dto.*;
import com.example.card.constrants.mapper.KioskMapper;
import com.example.card.constrants.model.Kiosk;
import com.example.card.repository.KioskRepository;
import com.example.card.services.impl.KioskServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class KioskServiceImplTest {

    @Mock
    private KioskRepository repository;

    @Mock
    private KioskMapper kioskMapper;

    @InjectMocks
    private KioskServiceImpl service;

    private KioskRequestDTO requestDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        GeoLocationDTO geoLocation = GeoLocationDTO.builder()
                .latitude(34.3023F)
                .longitude(75.8577F)
                .build();

        LocationDTO location = LocationDTO.builder()
                .streetName("MG Road")
                .townName("Indore")
                .country("India")
                .postCode("452001")
                .geoLocation(geoLocation)
                .build();

        HolidayCalendarDTO holiday1 = HolidayCalendarDTO.builder()
                .date("10-05-2025")
                .name("Saturday")
                .build();

        requestDTO = KioskRequestDTO.builder()
                .kioskId("K001")
                .branchId("B001")
                .name("Main Kiosk")
                .description("Primary branch kiosk")
                .location(location)
                .kioskServices(List.of("AccountOpening", "MoneyTransfer"))
                .openTime("09:00")
                .closeTime("18:00")
                .holidayCalendar(List.of(holiday1))
                .weeklyHolidays(List.of("Sunday"))
                .build();
    }

    @Test
    public void kioskSaveTest() {


        Kiosk kiosk = new Kiosk();
        Kiosk savedKiosk = new Kiosk();

        KioskResponseDTO responseDTO = new KioskResponseDTO();
               responseDTO.setKioskId("KX001");
               responseDTO.setBranchId("BX002");
               responseDTO.setName("DownTime Services");

        Mockito.when(kioskMapper.toEntity(requestDTO)).thenReturn(kiosk);
        Mockito.when(repository.save(kiosk)).thenReturn(savedKiosk);
        Mockito.when(kioskMapper.toDto(savedKiosk)).thenReturn(responseDTO);

        KioskResponseDTO result = service.createKiosk(requestDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDTO, result);
        Assertions.assertEquals(responseDTO.getKioskId(), result.getKioskId());
        Mockito.verify(kioskMapper).toDto(kiosk);
        Mockito.verify(repository).save(kiosk);

    }

    @Test
    public void createKiosk_shouldThrow_NullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            service.createKiosk(null);
        });
    }


}
