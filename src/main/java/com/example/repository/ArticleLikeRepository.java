package com.example.repository;

import com.example.entity.ArticleLikeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer>{
    ArticleLikeEntity findByProfileIdAndArticleId(Integer prtId, String articleId);

    @Query("delete from ArticleLikeEntity  where  id = :id")
    int delete(@Param("id") Integer id);

}
