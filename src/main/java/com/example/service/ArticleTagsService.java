package com.example.service;

import com.example.entity.ArticleTagsEntity;
import com.example.repository.ArticleTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTagsService {
    @Autowired
    private ArticleTagsRepository articleTagsRepository;

    public void create(String articleId, List<Integer> tagIdList) {
        tagIdList.forEach(tagId -> {
            create(articleId, tagId);
        });
    }

    public void create(String articleId, Integer tagId) {
        ArticleTagsEntity entity = new ArticleTagsEntity();
        entity.setArticleId(articleId);
        entity.setArticleTagId(tagId);
        articleTagsRepository.save(entity);
    }


    public void merge(String articleId, List<Integer> newList) {
        // newList = [1,2,7,8]
        if (newList == null) {
            articleTagsRepository.deleteByArticleId(articleId);
            return;
        }
        //[1,2,3,4,5]
        List<Integer> oldList = articleTagsRepository.getAllArticleTagIdList(articleId);
        for (Integer typeId : newList) {
            if (!oldList.contains(typeId)) {
                create(articleId, typeId); // create
            }
        }
        for (Integer typeId : oldList) {
            if (!newList.contains(typeId)) {
                articleTagsRepository.deleteByArticleIdAndTagId(articleId, typeId); // delete
            }
        }
    }
}
