package com.example.repository;

import com.example.entity.ArticleTypeEntity;
import com.example.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, UUID> , PagingAndSortingRepository<ArticleTypeEntity, UUID> {
    @Transactional
    @Modifying
    @Query("UPDATE ArticleTypeEntity e SET e = :newEntity WHERE e.id = :entityId")
    int updateAttribute(@Param("entityId") UUID entityId, @Param("newEntity") ArticleTypeEntity newEntity);

}
