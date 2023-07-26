package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Setter
@Getter
public class CommentEntity extends BaseStringEntity {
    private String content;

    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "article_id", nullable = false)
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

//    @Column(name = "reply_id", nullable = false)
//    private Integer replyId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "reply_id", insertable = false, updatable = false)
//    private ReplyEntity reply;

    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
