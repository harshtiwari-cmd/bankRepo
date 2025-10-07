package com.example.card.constrants.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "bank_details")
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column
    private String name;
    @Column
    private String mail;
    @Column
    private Long contact;
    @Column
    private String internationalContact;
    @Column
    private String instaUrl;
    @Column
    private String twitterUrl;
    @Column
    private String fbUrl;
    @Column
    private String dukhenBankUrl;
    @Column
    private String snapChatUrl;
    @Column
    private String youTubeUrl;

    @Column
    private String nameEn;
    @Column
    private String nameAr;
    @Column
    private String urlEn;
    @Column
    private String urlAr;
    @Column
    private Integer displayOrder;
    @Column(columnDefinition = "TEXT")
    private String followUsJson; // JSON serialized FollowUsItemDto list

}
