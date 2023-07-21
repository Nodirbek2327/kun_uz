package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ArticleStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.enums.ArticleStatus.Published;

public interface ArticleRepository extends CrudRepository<ArticleEntity, UUID>, PagingAndSortingRepository<ArticleEntity, UUID> {
    Optional<ArticleEntity> findByTitle(String title);

    @Transactional
    @Modifying
    @Query("UPDATE ArticleEntity e SET e = :newEntity WHERE e.id = :entityId")
    int updateAttribute(@Param("entityId") UUID entityId, @Param("newEntity") ArticleEntity newEntity);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = false where id =:id")
    int delete(@Param("id") UUID id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set status = :status where id =:id")
    int changeStatus(@Param("id") UUID id,  @Param("status") String status);


    @Transactional
    @Modifying
    @Query("from ArticleEntity where status = 'Published' and visible=true order by createdDate limit 5")
    List<ArticleEntity> getTop5();

    @Transactional
    @Modifying
    @Query("from ArticleEntity where status = 'Published' and visible=true order by createdDate limit 3")
    List<ArticleEntity> getTop3();


    @Transactional
    @Modifying
    @Query("from ArticleEntity where status = 'Published' and visible=true and  id NOT IN :excludedIds  order by createdDate limit 8")
    List<ArticleEntity> getLast8(@Param("excludedIds") List<UUID> excludedIds);


    @Transactional
    @Modifying
    @Query("from ArticleEntity where status = 'Published' and visible=true  and  id != :excludedIds  order by createdDate limit 4")
    List<ArticleEntity> getLast4(@Param("excludedIds") UUID excludedIds);


    @Transactional
    @Modifying
    @Query("from ArticleEntity where visible = true order by viewCount limit 4")
    List<ArticleEntity> mostRead4();
}
