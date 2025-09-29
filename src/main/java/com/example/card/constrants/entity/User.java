package com.example.card.constrants.entity;

import com.example.card.domain.model.Address;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_tbl")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String card;

    @Embedded
    private Address address;
}