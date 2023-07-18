package com.example.repository;

import com.example.entity.ArticleLikeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer>, PagingAndSortingRepository<ArticleLikeEntity, Integer> {
}
