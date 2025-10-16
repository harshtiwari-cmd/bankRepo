package com.example.card.adapter.api.services.impl;

import com.example.card.adapter.api.services.LocateUsService;
import com.example.card.constrants.entity.RbxTLocatorNewEntity;
import com.example.card.domain.dto.AtmResponseDto;
import com.example.card.domain.dto.BankBranchDTO;
import com.example.card.domain.dto.CoordinatesDTO;
import com.example.card.domain.dto.KioskResponseDTO;
import com.example.card.domain.dto.LocateUsDTO;
import com.example.card.repository.RbxTLocatorNewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "mock.enabled", havingValue = "false")
public class LocateUsServiceImpl implements LocateUsService {

    @Autowired
    private RbxTLocatorNewRepository repository;

    @Override
    public List<BankBranchDTO> fetchBranches() {
        List<RbxTLocatorNewEntity> rows = repository.findByLocatorTypeIgnoreCase("BRANCH");
        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }
        return rows.stream().map(this::mapToBranchDto).collect(Collectors.toList());
    }

    @Override
    public List<AtmResponseDto> fetchAtms() {
        List<RbxTLocatorNewEntity> rows = repository.findByLocatorTypeIgnoreCase("ATM");
        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }
        return rows.stream().map(this::mapToAtmDto).collect(Collectors.toList());
    }

    @Override
    public List<KioskResponseDTO> fetchKiosks() {
        List<RbxTLocatorNewEntity> rows = repository.findByLocatorTypeIgnoreCase("KIOSK");
        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }
        return rows.stream().map(this::mapToKioskDto).collect(Collectors.toList());
    }

    @Override
    public List<LocateUsDTO> fetchAllUnified() {
        List<RbxTLocatorNewEntity> rows = repository.findAll();
        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }
        return rows.stream().map(entity -> mapToUnifiedDto(entity, "")).collect(Collectors.toList());
    }

    @Override
    public List<LocateUsDTO> fetchByType(String locatorType, String lang) {
        List<RbxTLocatorNewEntity> rows = repository.findByLocatorTypeIgnoreCase(locatorType);
        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }

        return rows.stream().map(entity -> mapToUnifiedDto(entity, lang)).toList();
    }

    private BankBranchDTO mapToBranchDto(RbxTLocatorNewEntity e) {
        BankBranchDTO dto = new BankBranchDTO();
        dto.setId(null);
        dto.setBankName(null);
        dto.setBankBranchName(e.getArabicName());
        dto.setBranchNumber(e.getCode());
        dto.setDescription(e.getFacility());
        dto.setBankBranchType(e.getTypeLocation());
        dto.setCountryName(e.getCountry());
        dto.setCoordinates(null);
        dto.setOpenTime(null);
        dto.setAddress(e.getAddress());
        dto.setLocation(e.getCity());
        dto.setContact(e.getContactDetails());
        dto.setCloseTime(null);
        dto.setHolidayCalendar(null);
        dto.setWeeklyHolidays(null);
        dto.setStatus("ACTIVE");
        return dto;
    }

    private AtmResponseDto mapToAtmDto(RbxTLocatorNewEntity e) {
        return AtmResponseDto.builder()
                .id(null)
                .arabicName(e.getArabicName())
                .cashDeposit(e.getCashDeposit() != null && e.getCashDeposit() == 1)
                .cashOut(e.getCashOut() != null && e.getCashOut() == 1)
                .chequeDeposit(e.getChequeDeposit() != null && e.getChequeDeposit() == 1)
                .city(e.getCity())
                .cityInArabic(e.getCityInArabic())
                .code(e.getCode())
                .contactDetails(e.getContactDetails())
                .country(e.getCountry())
                .disablePeople(e.getDisablePeople() != null && e.getDisablePeople() == 1)
                .fullAddress(e.getFullAddress())
                .fullAddressArb(e.getFullAddressArb())
                .latitude(e.getLatitude())
                .longitude(e.getLongitude())
                .onlineLocation(e.getOnlineLocation() != null && e.getOnlineLocation() == 1)
                .timing(e.getTiming())
                .locatorType(e.getLocatorType())
                .workingHours(e.getWorkingHours())
                .workingHoursInArb(e.getWorkingHoursInArb())
                .searchString(e.getSearchString())
                .facility(e.getFacility())
                .address(e.getAddress())
                .atmType(e.getAtmType())
                .currencySupported(e.getCurrencySupported())
                .isActive(e.getIsActive())
                .installationDate(e.getInstallationDate())
                .maintenanceVendor(e.getMaintenanceVendor())
                .build();
    }

    private KioskResponseDTO mapToKioskDto(RbxTLocatorNewEntity e) {
        KioskResponseDTO dto = new KioskResponseDTO();
        dto.setKioskId(e.getCode());
        dto.setBranchId(null);
        dto.setName(e.getArabicName());
        dto.setDescription(e.getFacility());
        dto.setLocation(null);
        dto.setCoordinates(parseCoordinates(e));
        dto.setKioskServices(null);
        dto.setOpenTime(null);
        dto.setCloseTime(null);
        dto.setHolidayCalendar(null);
        dto.setWeeklyHolidays(null);
        return dto;
    }

    private LocateUsDTO mapToUnifiedDto(RbxTLocatorNewEntity e, String lang) {


        LocateUsDTO locateUsDTO = LocateUsDTO.builder()
                .locatorType(e.getLocatorType())
                .searchString(e.getSearchString())
                .coordinates(parseCoordinates(e))

                .facility(e.getFacility())
//                .address(e.getAddress())

                .cashDeposit(e.getCashDeposit())
                .cashOut(e.getCashOut())
                .chequeDeposit(e.getChequeDeposit())


                .code(e.getCode())
                .contactDetails(e.getContactDetails())
                .country(e.getCountry())
                .disablePeople(e.getDisablePeople())
                .fullAddress(e.getFullAddress())
                .onlineLocation(e.getOnlineLocation())
                .timing(e.getTiming())
                .typeLocation(e.getTypeLocation())


                .status(calculateStatus(e))

                .dateCreate(e.getDateCreate())
                .userCreate(e.getUserCreate())
                .dateModif(e.getDateModif())
                .userModif(e.getUserModif())

                .maintenanceVendor(e.getMaintenanceVendor())
                .atmType(e.getAtmType())
                .currencySupported(e.getCurrencySupported())
                .isActive(e.getIsActive())
                .installationDate(e.getInstallationDate())
                .build();

        if ("ar".equalsIgnoreCase(lang)) {
            locateUsDTO.setName(e.getArabicName());
            locateUsDTO.setFullAddress(e.getFullAddressArb());
            locateUsDTO.setCity(e.getCityInArabic());
            locateUsDTO.setWorkingHours(e.getWorkingHoursInArb());
        } else {
            locateUsDTO.setName(e.getName());
            locateUsDTO.setFullAddress(e.getFullAddress());
            locateUsDTO.setCity(e.getCity());
            locateUsDTO.setWorkingHours(e.getWorkingHours());
        }

        return locateUsDTO;
    }

    private CoordinatesDTO parseCoordinates(RbxTLocatorNewEntity e) {
        try {
            double lat = e.getLatitude() == null ? 0.0 : Double.parseDouble(e.getLatitude());
            double lon = e.getLongitude() == null ? 0.0 : Double.parseDouble(e.getLongitude());
            return CoordinatesDTO.builder().latitude(lat).longitude(lon).build();
        } catch (Exception ex) {
            return CoordinatesDTO.builder().latitude(0.0).longitude(0.0).build();
        }
    }

    private String calculateStatus(RbxTLocatorNewEntity e) {
        String working = e.getWorkingHours();
        if (working == null || working.isBlank()) {
            working = e.getTiming();
        }
        if (working == null) return "UNKNOWN";

        String normalized = normalizeWorkingHours(working);
        if (normalized.toUpperCase().contains("24/7") || normalized.toUpperCase().contains("24X7") || normalized.toUpperCase().contains("24 X 7")) {
            return "OPEN";
        }

        Map<DayOfWeek, List<TimeWindow>> schedule = parseSchedule(normalized);
        if (schedule.isEmpty()) return "CLOSED";

        ZoneId zoneId = ZoneId.of("Asia/Qatar"); // Force Qatar time
        LocalDateTime now = LocalDateTime.now(zoneId);
        LocalTime current = now.toLocalTime();
        DayOfWeek today = now.getDayOfWeek();

        List<TimeWindow> todays = schedule.getOrDefault(today, Collections.emptyList());
        for (TimeWindow w : todays) {
            boolean open;
            if (w.crossesMidnight) {
                open = !current.isBefore(w.start) || !current.isAfter(w.end);
            } else {
                open = !current.isBefore(w.start) && !current.isAfter(w.end);
            }
            if (open) return "OPEN";
        }

        return "CLOSED";
    }

// --- Helper classes & methods ---

    private static class TimeWindow {
        final LocalTime start;
        final LocalTime end;
        final boolean crossesMidnight;
        TimeWindow(LocalTime start, LocalTime end, boolean crossesMidnight) {
            this.start = start;
            this.end = end;
            this.crossesMidnight = crossesMidnight;
        }
    }

    private String normalizeWorkingHours(String text) {
        if (text == null) return "";
        return text
                .replace("\\n", "\n")
                .replace("\\N", "\n")
                .replace('–', '-')  // en dash
                .replace('—', '-')  // em dash
                .replaceAll("\\s+to\\s+", " TO ")
                .replaceAll("\\s*-\\s*", "-")
                .replaceAll("\\s*/\\s*", "/") // keep slash for multiple windows
                .trim();
    }

    private Map<DayOfWeek, List<TimeWindow>> parseSchedule(String text) {
        if (text == null || text.isBlank()) return Collections.emptyMap();
        Map<DayOfWeek, List<TimeWindow>> schedule = new EnumMap<>(DayOfWeek.class);

        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.isBlank()) continue;
            int idx = firstTimeIndex(line);
            if (idx == -1) continue;

            String dayPart = line.substring(0, idx).trim();
            String timePart = line.substring(idx).trim();

            List<DayOfWeek> days = parseDays(dayPart);
            if (days.isEmpty()) continue;

            String[] windows = timePart.split("\\s*/\\s*|\\s*;\\s*|\\s*,\\s*");
            for (String w : windows) {
                TimeWindow tw = parseTimeWindowOnly(w.trim());
                if (tw != null) {
                    for (DayOfWeek d : days) {
                        schedule.computeIfAbsent(d, k -> new ArrayList<>()).add(tw);
                    }
                }
            }
        }

        return schedule;
    }

    private int firstTimeIndex(String line) {
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (Character.isDigit(c)) return i;
        }
        return -1;
    }

    private List<DayOfWeek> parseDays(String dayPart) {
        if (dayPart == null || dayPart.isBlank()) return Collections.emptyList();
        String dp = dayPart.toUpperCase(Locale.ROOT)
                .replace("&", ",")
                .replace(" AND ", ",")
                .replaceAll("\\s*-\\s*", " TO ")
                .replaceAll("\\s+TO\\s+", " TO ")
                .trim();

        List<DayOfWeek> result = new ArrayList<>();

        if (dp.contains(" TO ")) {
            String[] parts = dp.split(" TO ");
            if (parts.length == 2) {
                DayOfWeek start = dayNameToDOW(parts[0].trim());
                DayOfWeek end = dayNameToDOW(parts[1].trim());
                if (start != null && end != null) {
                    int s = start.getValue();
                    while (true) {
                        DayOfWeek d = DayOfWeek.of(s);
                        result.add(d);
                        if (d == end) break;
                        s = s % 7 + 1;
                    }
                }
            }
        }

        for (String part : dp.split(",")) {
            DayOfWeek d = dayNameToDOW(part.trim());
            if (d != null && !result.contains(d)) result.add(d);
        }

        return result;
    }

    private DayOfWeek dayNameToDOW(String s) {
        String t = s.trim().toLowerCase(Locale.ROOT);
        if (t.startsWith("sun")) return DayOfWeek.SUNDAY;
        if (t.startsWith("mon")) return DayOfWeek.MONDAY;
        if (t.startsWith("tue")) return DayOfWeek.TUESDAY;
        if (t.startsWith("wed")) return DayOfWeek.WEDNESDAY;
        if (t.startsWith("thu")) return DayOfWeek.THURSDAY;
        if (t.startsWith("fri")) return DayOfWeek.FRIDAY;
        if (t.startsWith("sat")) return DayOfWeek.SATURDAY;
        return null;
    }

    private TimeWindow parseTimeWindowOnly(String text) {
        if (text == null || text.isBlank()) return null;
        String cleaned = text.replace("to", "-").replace("TO", "-").trim();
        String[] parts = cleaned.split("\\s*-\\s*");
        if (parts.length < 2) return null;

        LocalTime start = parseTime(parts[0].trim());
        LocalTime end = parseTime(parts[1].trim());
        if (start == null || end == null) return null;

        boolean crossesMidnight = end.isBefore(start);
        return new TimeWindow(start, end, crossesMidnight);
    }

    private LocalTime parseTime(String s) {
        if (s == null || s.isBlank()) return null;
        String normalized = s.trim().toUpperCase().replace("AM", " AM").replace("PM", " PM").replace("  ", " ");
        List<DateTimeFormatter> formatters = List.of(
                DateTimeFormatter.ofPattern("H:mm"),
                DateTimeFormatter.ofPattern("HH:mm"),
                DateTimeFormatter.ofPattern("h:mm a"),
                DateTimeFormatter.ofPattern("hh:mm a"),
                DateTimeFormatter.ofPattern("h a"),
                DateTimeFormatter.ofPattern("hh a"),
                DateTimeFormatter.ofPattern("ha")
        );
        for (DateTimeFormatter f : formatters) {
            try {
                return LocalTime.parse(normalized, f);
            } catch (Exception ignored) {}
        }
        return null;
    }
}


