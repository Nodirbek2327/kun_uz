package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class CategoryDTO {
    private UUID id;
    private Integer order_number;
    private String name_uz;
    private String name_ru;
    private String name_eng;
    private Boolean visible;
    private LocalDateTime createdDate;
}
