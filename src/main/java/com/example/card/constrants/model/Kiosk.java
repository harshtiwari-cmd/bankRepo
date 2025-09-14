package com.example.card.constrants.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Kiosk {

    @Id
    private String kioskId;
    private String branchId;
    private String name;
    private String description;

    @Embedded
    private Location location;

    @ElementCollection
    @CollectionTable(name = "kiosk_services", joinColumns = @JoinColumn(name = "kiosk_id"))
    @Column(name = "services")
    private List<String> kioskServices;

    private String openTime;
    private String closeTime;

    @OneToMany(mappedBy = "kiosk", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<HolidayCalendar> holidayCalendar;

    private List<String> weeklyHolidays;



}
