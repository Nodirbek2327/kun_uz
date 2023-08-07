package com.example.dto;

import com.example.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SavedArticleDTO {
    private Integer id;
    private Integer profileId;
    private String articleId;
    private LocalDateTime createdDate;
}
