package com.example.card.adapter.api.services.impl;

import com.example.card.constrants.entity.RbxTLocatorNewEntity;
import com.example.card.domain.dto.CoordinatesDTO;
import com.example.card.domain.dto.LocateUsDTO;
import com.example.card.repository.RbxTLocatorNewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocateUsServiceImplTest {

    @Mock
    private RbxTLocatorNewRepository repository;

    @InjectMocks
    private LocateUsServiceImpl locateUsService;

    private LocateUsDTO branchDTO;
    private LocateUsDTO kioskDto;
    private LocateUsDTO atmDto;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @BeforeEach
    void setUp() throws ParseException {

        // BRANCH
        branchDTO = LocateUsDTO.builder()
                .locatorType("BRANCH")
                .searchString(null)
                .coordinates(CoordinatesDTO.builder()
                        .latitude(25.28461111)
                        .longitude(51.50636389)
                        .build())
                .facility(null)
                .address(null)
                .arabicName("فرع السد")
                .cashDeposit(0)
                .cashOut(0)
                .chequeDeposit(0)
                .city("Doha")
                .cityInArabic("الدوحة")
                .code("3")
                .contactDetails("8008555.0")
                .country("Qatar")
                .disablePeople(0)
                .fullAddress("Al Sadd Branch")
                .fullAddressArb("فرع السد")
                .onlineLocation(0)
                .timing("Sunday|7:30|Thursday|13:00")
                .typeLocation(null)
                .workingHours("Sunday to Thursday: 7:30am - 1:00pm\\n")
                .workingHoursInArb("الأحد إلى الخميس من 7:30 صباحاً حتى 1:00 مساءً")
                .status("CLOSED")
                .dateCreate(format.parse("2024-10-27T08:34:00.000+00:00"))
                .userCreate("APPDATA")
                .dateModif(null)
                .userModif(null)
                .maintenanceVendor(null)
                .atmType("BRANCH")
                .currencySupported(null)
                .isActive(null)
                .installationDate(null)
                .build();

        // KIOSK
        kioskDto = LocateUsDTO.builder()
                .locatorType("KIOSK")
                .searchString(null)
                .coordinates(CoordinatesDTO.builder()
                        .latitude(25.28461111)
                        .longitude(51.50636389)
                        .build())
                .facility(null)
                .address(null)
                .arabicName("Dukhan - Alsad")
                .cashDeposit(0)
                .cashOut(0)
                .chequeDeposit(0)
                .city("DOHA")
                .cityInArabic("الدوحه")
                .code("4111")
                .contactDetails("8008555.0")
                .country("Qatar")
                .disablePeople(0)
                .fullAddress("Dukhan - Alsad")
                .fullAddressArb("فرع السد")
                .onlineLocation(1)
                .timing(null)
                .typeLocation(null)
                .workingHours("24/7")
                .workingHoursInArb("24/7")
                .status("OPEN")
                .dateCreate(format.parse("2024-07-09T07:51:00.000+00:00"))
                .userCreate("SYS")
                .dateModif(format.parse("2025-06-01T04:45:00.000+00:00"))
                .userModif("APPDATA")
                .maintenanceVendor(null)
                .atmType("KIOSK")
                .currencySupported(null)
                .isActive(null)
                .installationDate(null)
                .build();

        // ATM
        atmDto = LocateUsDTO.builder()
                .locatorType("ATM")
                .coordinates(CoordinatesDTO.builder()
                        .latitude(25.232232)
                        .longitude(51.574471)
                        .build())
                .arabicName(null)
                .cashDeposit(1)
                .cashOut(1)
                .chequeDeposit(1)
                .city("DOHA")
                .cityInArabic("الدوحه")
                .code("3055")
                .contactDetails("8008555.0")
                .country("QATAR")
                .disablePeople(0)
                .fullAddress("DUKHAN - AMAN HOSPITAL")
                .fullAddressArb(null)
                .onlineLocation(1)
                .workingHours("24/7")
                .workingHoursInArb("24/7")
                .status("OPEN")
                .dateCreate(format.parse("2023-12-20T03:41:00.000+00:00"))
                .userCreate("OCIETLUSR")
                .dateModif(format.parse("2024-12-15T06:59:00.000+00:00"))
                .userModif("APPDATA")
                .atmType("ATM")
                .build();
    }

    @Test
    void fetchByType_ShouldReturnBranches_WhenTypeIsBranch() {

        RbxTLocatorNewEntity entity = RbxTLocatorNewEntity.builder()
                .code("001")
                .locatorType("BRANCH")
                .latitude("25.276987")
                .longitude("55.296249")
                .city("Dubai")
                .country("UAE")
                .isActive("Y")
                .dateCreate(new Date())
                .build();

        // Arrange
        when(repository.findByLocatorTypeIgnoreCase("BRANCH")).thenReturn(List.of(entity));

        // Act
        List<LocateUsDTO> result = locateUsService.fetchByType("BRANCH");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("BRANCH", result.get(0).getLocatorType());
        assertEquals("Dubai", result.get(0).getCity());
        assertEquals("UAE", result.get(0).getCountry());

    }

    @Test
    void fetchByType_ShouldReturnKiosks_WhenTypeIsKiosk() {

        RbxTLocatorNewEntity entity = RbxTLocatorNewEntity.builder()
                .code("002")
                .locatorType("KIOSK")
                .latitude("25.276987")
                .longitude("55.296249")
                .city("DOHA")
                .country("QATAR")
                .isActive("Y")
                .cashOut(1)
                .dateCreate(new Date())
                .build();

        // Arrange
        when(repository.findByLocatorTypeIgnoreCase("KIOSK")).thenReturn(List.of(entity));

        // Act
        List<LocateUsDTO> result = locateUsService.fetchByType("KIOSK");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("KIOSK", result.get(0).getLocatorType());
        assertEquals("DOHA", result.get(0).getCity());
        assertEquals("QATAR", result.get(0).getCountry());

        verify(repository, times(1)).findByLocatorTypeIgnoreCase("KIOSK");
    }

    @Test
    void fetchByType_ShouldReturnAtms_WhenTypeIsAtm() {

        RbxTLocatorNewEntity entity = RbxTLocatorNewEntity.builder()
                .code("003")
                .locatorType("ATM")
                .latitude("25.276987")
                .longitude("55.296249")
                .city("DOHA")
                .country("QATAR")
                .isActive("Y")
                .cashOut(1)
                .dateCreate(new Date())
                .build();
        // Arrange
        when(repository.findByLocatorTypeIgnoreCase("ATM")).thenReturn(List.of(entity));

        // Act
        List<LocateUsDTO> result = locateUsService.fetchByType("ATM");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ATM", result.get(0).getLocatorType());
        assertEquals("DOHA", result.get(0).getCity());
        assertEquals("QATAR", result.get(0).getCountry());

        verify(repository, times(1)).findByLocatorTypeIgnoreCase("ATM");
    }

    @Test
    void fetchByType_ShouldReturnEmptyList_WhenNoDataExists() {

        when(repository.findByLocatorTypeIgnoreCase("BRANCH")).thenReturn(Collections.emptyList());

        List<LocateUsDTO> result = locateUsService.fetchByType("BRANCH");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository, times(1)).findByLocatorTypeIgnoreCase("BRANCH");
    }

}