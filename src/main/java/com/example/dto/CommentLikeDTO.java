package com.example.dto;

import com.example.enums.CommentLikeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentLikeDTO {
    private String id;
    private Integer profileId;
    private String commentId;
    private String articleId;
    private LocalDateTime createdDate;
    private CommentLikeStatus status;
}
