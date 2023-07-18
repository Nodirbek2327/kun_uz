package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ArticleTypeDTO {
    private Integer id;
    private Integer order_number;
    private String nameUz;
    private String nameRu;
    private String nameEng;
    private Boolean visible;
    private LocalDateTime createdDate;
}
