package com.example.dto;

import com.example.entity.*;
import com.example.enums.ArticleStatus;
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
    private Long sharedCount;
    private Integer imageId;
    private List<RegionEntity> regionId;
    private List<CategoryEntity> categoryId;
    private ProfileEntity moderatorId;
    private ProfileEntity publisherId;
    private List<ArticleTypeEntity> articleTypes;
    private List<TagEntity> articleTags;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Long view_count;
}
