package com.example.dto;

import com.example.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ArticleFilterDTO {
    private String id;
    private String title;
    private Integer category_id;
    private Integer region_id;
    private Integer moderator_id;
    private Integer publisher_id;
    private ArticleStatus status;
    private LocalDate created_date_from;
    private LocalDate created_date_to;
    private LocalDate published_date_from;
    private LocalDate published_date_to;
}
