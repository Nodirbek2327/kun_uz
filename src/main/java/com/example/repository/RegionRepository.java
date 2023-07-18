package com.example.repository;

import com.example.entity.RegionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface RegionRepository extends CrudRepository<RegionEntity, Integer>, PagingAndSortingRepository<RegionEntity, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE RegionEntity e SET e = :newEntity WHERE e.id = :entityId")
    int updateAttribute(@Param("entityId") Integer entityId, @Param("newEntity") RegionEntity newEntity);
    @Transactional
    @Modifying
    @Query("update RegionEntity set visible = false where id =:id")
    int delete(@Param("id") Integer id);
}
