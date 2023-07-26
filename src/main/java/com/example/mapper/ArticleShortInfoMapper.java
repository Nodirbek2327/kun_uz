package com.example.mapper;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ArticleShortInfoMapper {
    private String id;
    private String title;
    private LocalDateTime publishedDate;
    private String imageId;

    public ArticleShortInfoMapper(String id, String title, String imageId, LocalDateTime publishedDate) {
        this.id = id;
        this.title = title;
        this.publishedDate = publishedDate;
        this.imageId = imageId;
    }
}
