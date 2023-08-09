package com.example.repository;

import com.example.entity.ArticleTypeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> , PagingAndSortingRepository<ArticleTypeEntity, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE ArticleTypeEntity e SET e = :newEntity WHERE e.id = :entityId")
    int updateAttribute(@Param("entityId") Integer entityId, @Param("newEntity") ArticleTypeEntity newEntity);

    @Transactional
    @Modifying
    @Query("update ArticleTypeEntity set visible = false where id =:id")
    int delete(@Param("id") Integer id);

    Iterable<ArticleTypeEntity> findAllByVisibleIsTrue();
    Page<ArticleTypeEntity> findAllByVisibleIsTrue(Pageable pageable);
}
