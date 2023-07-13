package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "category")
public class CategoryEntity {
    @Id
    private UUID id;
    private Integer order_number;
    private String name_uz;
    private String name_ru;
    private String name_eng;
    private Boolean visible;
    private LocalDateTime createdDate;
}
