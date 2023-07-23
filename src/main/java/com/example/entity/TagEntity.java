package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "tag")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer order_number;
    @Column(name = "name_uz", nullable = false)
    private String nameUz;
    @Column(name = "name_ru", nullable = false)
    private String nameRu;
    @Column(name = "name_eng", nullable = false)
    private String nameEng;
    @Column(nullable = false)
    private Boolean visible=true;
    @Column(name = "prt_id")
    private Integer prtId;
    private LocalDateTime createdDate= LocalDateTime.now();
}
