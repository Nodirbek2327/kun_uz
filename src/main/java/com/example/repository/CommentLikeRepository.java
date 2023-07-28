package com.example.repository;

import com.example.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, String> {
    @Query("delete from CommentLikeEntity  where  id = :id")
    int delete(@Param("id") String id);

    CommentLikeEntity findByProfileIdAndCommentIdOrArticleId(Integer prtId, String commentId, String articleId);
}
