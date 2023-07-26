package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "article_tags")
public class ArticleTagsEntity extends BaseEntity{
    @Column(name = "article_id", nullable = false)
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @Column(name = "article_tag_id", nullable = false)
    private Integer articleTagId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_tag_id", insertable = false, updatable = false)
    private TagEntity articleTag;
}
