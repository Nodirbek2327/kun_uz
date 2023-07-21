package com.example.dto;

import com.example.entity.CategoryEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Predicate;

@Getter
@Setter
public class ArticleDTO {
    private UUID id;
    private String title;
    private String description;
    private String content;
    private Long sharedCount;
    private Integer imageId;
    private RegionEntity regionId;
    private CategoryEntity categoryId;
    private ProfileEntity moderatorId;
    private ProfileEntity publisherId;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Long view_count;
}
