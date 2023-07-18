package com.example.entity;

import com.example.enums.ArticleLikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "article_like")
public class ArticleLikeEntity {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "id")
    private ProfileEntity profileId;
    private Integer articleId;
    private LocalDateTime createdDate;
    private ArticleLikeStatus status;

}
