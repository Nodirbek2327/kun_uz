package com.example.entity;

import com.example.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    private String id;
    private String title;
    private String description;
    private String content;
    @Column(name = "shared_count")
    private Long sharedCount;
    @ManyToMany
    @JoinTable(
            name = "region_id",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<RegionEntity> regionId;
    @ManyToMany
    @JoinTable(
            name = "category_id",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<CategoryEntity> categoryId;
    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private ProfileEntity moderatorId;
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private ProfileEntity publisherId;
    @ManyToMany
    @JoinTable(
            name = "article_types",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<ArticleTypeEntity> articleTypes;
    @ManyToMany
    @JoinTable(
            name = "article_tags",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<TagEntity> articleTags;
    @Enumerated(value = EnumType.STRING)
    private ArticleStatus status;
    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();
    @Column(name = "published_date")
    private LocalDateTime publishedDate;
    private Boolean visible=true;
    @Column(name = "view_count")
    private Long viewCount=0l;
    @Column(name = "prt_id")
    private Integer prtId;
    //    @Column(name = "image_id")
//    private Integer imageId;
}
