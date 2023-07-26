package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
public class CommentFilterDTO {
    private String id;
    private Integer profileId;
    private String articleId;
    private LocalDate created_date_from;
    private LocalDate created_date_to;
}
