package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
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


//    @Query("from ArticleEntity as a " +
//            " inner join a.articleTypeSet as at" +
//            " where at.articleTypeId =:articleTypeId  and a.status =:status and a.visible = true " +
//            " order by a.publishedDate desc limit :limit")
//    List<ArticleEntity> getLast5ArticleByArticleTypeId(@Param("articleTypeId") Integer articleTypeId,
//                                                       @Param("status") ArticleStatus status,
//                                                       @Param("limit") int limit);

    @Query(value = "select a.id, a.title, a.description, a.image_id, a.published_date from article as a " +
            " inner join article_types as at on at.article_id = a.id" +
            " where at.article_type_id =:articleTypeId  and a.status ='PUBLISHED' and a.visible = true " +
            " order by a.published_date desc limit :limit", nativeQuery = true)
    List<ArticleShortInfoMapper> getLast5ArticleByArticleTypeIdNative(@Param("articleTypeId") Integer articleTypeId,
                                                                      @Param("limit") int limit);


    @Query("select a.id, a.title, a.description, a.imageId, a.publishedDate  from ArticleEntity as a " +
            " where a.id not in :excludedIds  and a.status =:status and a.visible = true " +
            " order by a.publishedDate desc limit 8")
    List<ArticleShortInfoMapper> getLast8ArticleExceptSome(@Param("excludedIds") List<String> excludedIds,
                                                       @Param("status") ArticleStatus status);



    //9. Get Last 4 Article By Types and except given article id.
    @Query("select a.id, a.title, a.description, a.imageId, a.publishedDate from ArticleEntity as a " +
            " inner join a.articleTypeSet as at" +
            " where at.articleTypeId =:articleTypeId and a.id <>:articleId and a.status =:status and a.visible = true " +
            " order by a.publishedDate desc limit 4 ")
    List<ArticleShortInfoMapper> getLast4ArticleByArticleTypeIdAndExcept(@Param("articleId") String articleId,
                                                                @Param("articleTypeId") Integer articleTypeId,
                                                                @Param("status") ArticleStatus status);


    @Query("select a.id, a.title, a.description, a.imageId, a.publishedDate  from ArticleEntity as a " +
            " where a.status =:status and a.visible = true " +
            " order by a.viewCount desc limit 4")
    List<ArticleShortInfoMapper> get4MostRead(@Param("status") ArticleStatus status);


    @Query("select a.id, a.title, a.description, a.imageId, a.publishedDate from ArticleEntity as a " +
            " inner join a.articleTagSet as at" +
            " where at.articleTagId =:articleTagId and a.id <> :articleId  and a.status =:status and a.visible = true " +
            " order by a.publishedDate desc limit 4 ")
    List<ArticleShortInfoMapper> getLast4ArticleByArticleTagId(@Param("articleId") String articleId,
                                                                         @Param("articleTagId") Integer articleTagId,
                                                                         @Param("status") ArticleStatus status);

    @Query("select a.id, a.title, a.description, a.imageId, a.publishedDate from ArticleEntity as a " +
            " inner join a.articleTypeSet as at" +
            " where at.articleTypeId =:articleTypeId and a.regionId = :regionId and a.status =:status and a.visible = true " +
            " order by a.publishedDate desc limit 5 ")
    List<ArticleShortInfoMapper> getLast5ArticleByArticleTypeIdAndRegionId(@Param("regionId") Integer regionId,
                                                                         @Param("articleTypeId") Integer articleTypeId,
                                                                         @Param("status") ArticleStatus status);

    @Query("select a.id, a.title, a.description, a.imageId, a.publishedDate from ArticleEntity as a " +
            " where a.regionId = :regionId and a.status =:status and a.visible = true ")
    PageImpl<ArticleShortInfoMapper> getPaginationByRegionId(@Param("regionId") Integer regionId,
                                                         @Param("status") ArticleStatus status,
                                                         Pageable pageable);

    @Query("select a.id, a.title, a.description, a.imageId, a.publishedDate from ArticleEntity as a " +
            " where a.categoryId = :categoryId and a.status =:status and a.visible = true "+
            " order by a.publishedDate desc limit 5 ")
    List<ArticleShortInfoMapper> getLast5ByCategoryId(@Param("categoryId") Integer id,
                                                     @Param("status") ArticleStatus status);


    @Query("select a.id, a.title, a.description, a.imageId, a.publishedDate from ArticleEntity as a " +
            " where a.categoryId = :categoryId and a.status =:status and a.visible = true ")
    PageImpl<ArticleShortInfoMapper> getPaginationByCategoryId(@Param("categoryId") Integer categoryId,
                                                             @Param("status") ArticleStatus status,
                                                             Pageable pageable);


    @Transactional
    @Modifying
    @Query("update ArticleEntity set viewCount = viewCount+1 where id =:id")
    int increaseViewCount(@Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set sharedCount = sharedCount+1 where id =:id")
    int increaseSharedCount(@Param("id") String id);



//    @Transactional
//    @Modifying
//    @Query("select new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a join a.articleTypes as t where t.id = :id and a.status = 'Published' and a.visible=true  order by a.createdDate limit 5")
//    List<ArticleShortInfoMapper> getLast5(@Param("id") Integer id);
//
//    @Transactional
//    @Modifying
//    @Query("select new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a join a.articleTypes as t where t.id = :id and a.status = 'Published' and a.visible=true order by a.createdDate limit 3")
//    List<ArticleShortInfoMapper> getLast3(@Param("id") Integer id);
//
//
//    @Transactional
//    @Modifying
//    @Query("select new com.example.mapper.ArticleShortInfoMapper(id, title, publishedDate) from ArticleEntity  where status = 'Published' and visible=true and  id NOT IN :excludedIds  order by createdDate limit 8")
//    List<ArticleShortInfoMapper> getLast8(@Param("excludedIds") List<String> excludedIds);
//
//
//    @Transactional
//    @Modifying
//    @Query("select new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a join a.articleTypes as t where t.id = :id and a.status = 'Published' and a.visible=true  and  a.id != :excludedIds  order by a.createdDate limit 4")
//    List<ArticleShortInfoMapper> getLast4(@Param("id") Integer id , @Param("excludedIds") String excludedIds);
//
//
//    @Transactional
//    @Modifying
//    @Query("from ArticleEntity where visible = true order by viewCount limit 4")
//    List<ArticleShortInfoMapper> mostRead4();
//
////    @Transactional
////    @Modifying
////    @Query("select new com.example.mapper.ArticleShortInfoMapper(id, title, publishedDate) from ArticleEntity  where id = :id and status = 'Published' and visible=true  and  id != :excludedIds  order by createdDate limit 4")
////    List<ArticleShortInfoMapper> getLast4(@Param("name") String name , @Param("excludedIds") String excludedIds);
//
//    @Transactional
//    @Modifying
//    @Query("select  new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a JOIN a.articleTags as t  WHERE t.nameUz = :tagName OR t.nameEng = :tagName OR t.nameRu = :tagName and a.status = 'Published' and a.visible=true  order by a.createdDate desc limit 4")
//    List<ArticleShortInfoMapper> getLast4ByTag(@Param("tagName") String tagName);
//
//    @Transactional
//    @Modifying
//    @Query("select  new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a JOIN a.articleTypes  as t join a.regionId as r WHERE t.nameUz = :type OR t.nameEng = :type OR t.nameRu = :type and  r.id = :id and a.visible=true  order by a.createdDate desc limit 5")
//    List<ArticleShortInfoMapper> getLast5ByTypeAndRegion(@Param("id") Integer id, @Param("type") String type);
//
//    @Transactional
//    @Modifying
//    @Query("select  new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a join  a.regionId as r where r.id = :id and a.visible=true ")
//    PageImpl<ArticleShortInfoMapper> getListRegionPagination(@Param("id") Integer id, Pageable pageable);
//
//    @Transactional
//    @Modifying
//    @Query("select  new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a  join  a.categoryId as c where c.id = :id and a.visible=true  order by a.createdDate desc limit 5")
//    List<ArticleShortInfoMapper> getLast5ByCategory(@Param("id") Integer id);
//
//    @Transactional
//    @Modifying
//    @Query("select  new com.example.mapper.ArticleShortInfoMapper(a.id, a.title, a.publishedDate) from ArticleEntity as a  join  a.categoryId as c where  c.id = :id and a.visible=true ")
//    PageImpl<ArticleShortInfoMapper> getListCategoryPagination(@Param("id") Integer id, Pageable pageable);

}
