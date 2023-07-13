package com.example.repository;


import com.example.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CategoryRepository extends CrudRepository<CategoryEntity, UUID>, PagingAndSortingRepository<CategoryEntity, UUID> {

    @Transactional
    @Modifying
    @Query("UPDATE CategoryEntity e SET e = :newEntity WHERE e.id = :entityId")
    int updateAttribute(@Param("entityId") UUID entityId, @Param("newEntity") CategoryEntity newEntity);


}
