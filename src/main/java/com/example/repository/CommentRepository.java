package com.example.repository;

import com.example.entity.CommentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, String> {
    @Transactional
    @Modifying
    @Query("update CommentEntity set visible = false where id =:id")
    int delete(@Param("id") String id);

    List<CommentEntity> findByArticleId(String articleId);

    @Query("from CommentEntity as a where a.visible = true")
    PageImpl<CommentEntity> getPagination(Pageable pageable);
}
