package com.example.mapper;

import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class ArticleFullInfoMapper {
    private String id;
    private String title;
    private String description;
    private String content;
    private Long sharedCount;
    private Integer imageId;
    private RegionEntity region;
    private CategoryEntity category;
    private LocalDateTime publishedDate;
    private Long view_count;
    private Long like_count;
}
