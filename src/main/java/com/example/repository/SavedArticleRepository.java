package com.example.repository;

import com.example.entity.ArticleLikeEntity;
import com.example.entity.SavedArticleEntity;
import com.example.enums.LikeStatus;
import com.example.mapper.SavedArticleMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity, Integer> {

    @Query(value = "select s.id, a as article from saved_article as s inner join article as a on s.article_id = a.article_id" +
            " where s.profile_id = :prtId order by s.created_date ", nativeQuery = true)
    List<SavedArticleMapper> findAllByProfileId(@Param("prtId") Integer prtId);

    int deleteByArticleIdAndProfileId(String articleId, Integer id);
}
