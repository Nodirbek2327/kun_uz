package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.mapper.ArticleShortInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String>, PagingAndSortingRepository<ArticleEntity, String> {
    Optional<ArticleEntity> findByTitle(String title);

    @Transactional
    @Modifying
    @Query("UPDATE ArticleEntity e SET e = :newEntity WHERE e.id = :entityId")
    int updateAttribute(@Param("entityId") String entityId, @Param("newEntity") ArticleEntity newEntity);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = false where id =:id")
    int delete(@Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set status = :status where id =:id")
    int changeStatus(@Param("id") String id,  @Param("status") String status);


    @Transactional
    @Modifying
    @Query("select new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a join a.articleTypes as t where t.id = :id and a.status = 'Published' and a.visible=true  order by a.createdDate limit 5")
    List<ArticleShortInfoMapper> getLast5(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("select new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a join a.articleTypes as t where t.id = :id and a.status = 'Published' and a.visible=true order by a.createdDate limit 3")
    List<ArticleShortInfoMapper> getLast3(@Param("id") Integer id);


    @Transactional
    @Modifying
    @Query("select new com.example.mapper.ArticleShortInfoMapper(id, title, publishedDate) from ArticleEntity  where status = 'Published' and visible=true and  id NOT IN :excludedIds  order by createdDate limit 8")
    List<ArticleShortInfoMapper> getLast8(@Param("excludedIds") List<String> excludedIds);


    @Transactional
    @Modifying
    @Query("select new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a join a.articleTypes as t where t.id = :id and a.status = 'Published' and a.visible=true  and  a.id != :excludedIds  order by a.createdDate limit 4")
    List<ArticleShortInfoMapper> getLast4(@Param("id") Integer id , @Param("excludedIds") String excludedIds);


    @Transactional
    @Modifying
    @Query("from ArticleEntity where visible = true order by viewCount limit 4")
    List<ArticleShortInfoMapper> mostRead4();

//    @Transactional
//    @Modifying
//    @Query("select new com.example.mapper.ArticleShortInfoMapper(id, title, publishedDate) from ArticleEntity  where id = :id and status = 'Published' and visible=true  and  id != :excludedIds  order by createdDate limit 4")
//    List<ArticleShortInfoMapper> getLast4(@Param("name") String name , @Param("excludedIds") String excludedIds);

    @Transactional
    @Modifying
    @Query("select  new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a JOIN a.articleTags as t  WHERE t.nameUz = :tagName OR t.nameEng = :tagName OR t.nameRu = :tagName and a.status = 'Published' and a.visible=true  order by a.createdDate desc limit 4")
    List<ArticleShortInfoMapper> getLast4ByTag(@Param("tagName") String tagName);

    @Transactional
    @Modifying
    @Query("select  new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a JOIN a.articleTypes  as t join a.regionId as r WHERE t.nameUz = :type OR t.nameEng = :type OR t.nameRu = :type and  r.id = :id and a.visible=true  order by a.createdDate desc limit 5")
    List<ArticleShortInfoMapper> getLast5ByTypeAndRegion(@Param("id") Integer id, @Param("type") String type);

    @Transactional
    @Modifying
    @Query("select  new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a join  a.regionId as r where r.id = :id and a.visible=true ")
    PageImpl<ArticleShortInfoMapper> getListRegionPagination(@Param("id") Integer id, Pageable pageable);

    @Transactional
    @Modifying
    @Query("select  new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a  join  a.categoryId as c where c.id = :id and a.visible=true  order by a.createdDate desc limit 5")
    List<ArticleShortInfoMapper> getLast5ByCategory(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("select  new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a  join  a.categoryId as c where  c.id = :id and a.visible=true ")
    PageImpl<ArticleShortInfoMapper> getListCategoryPagination(@Param("id") Integer id, Pageable pageable);

}
