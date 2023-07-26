package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentDTO {
    private String id;
    private String content;
    private Integer profileId;
    private String articleId;
    private Boolean visible;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
