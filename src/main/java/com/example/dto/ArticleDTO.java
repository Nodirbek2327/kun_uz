package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ArticleDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private String imageId;
    private Integer categoryId;
    private List<Integer> articleType;
    private Integer regionId;
    private RegionDTO region;
    private LocalDateTime createdDate;
}
