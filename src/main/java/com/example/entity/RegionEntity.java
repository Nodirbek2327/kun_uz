package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "region")
public class RegionEntity {
    @Id
    private UUID id;
    private Integer order_number;
    @Column(nullable = false)
    private String name_uz;
    @Column(nullable = false)
    private String name_ru;
    @Column(nullable = false)
    private Boolean visible;
    private LocalDateTime createdDate;
}
