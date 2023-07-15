package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "articleType")
public class ArticleTypeEntity {
    @Id
    private UUID id;
    private Integer order_number;
    @Column(nullable = false)
    private String name_uz;
    @Column(nullable = false)
    private String name_ru;
    @Column(nullable = false)
    private String name_eng;
    private Boolean visible;
    private LocalDateTime createdDate;
}
