package com.example.dto;

import com.example.enums.ArticleLikeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleLikeDTO {
   private String id;
   private String profileId;
   private String articleId;
   private LocalDateTime createdDate;
   private ArticleLikeStatus status;
}
