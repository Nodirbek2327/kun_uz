package com.example.service;

import com.example.dto.ArticleLikeDTO;
import com.example.entity.ArticleLikeEntity;
import com.example.enums.LikeStatus;
import com.example.exp.AppBadRequestException;
import com.example.repository.ArticleLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeService {

    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public ArticleLikeDTO createLike(ArticleLikeDTO dto, Integer prtId) {
        check(dto);
        ArticleLikeEntity entity = get(dto.getArticleId(), prtId);
        if (entity != null){
            delete(entity.getId());
        }
        ArticleLikeEntity entity2 = new ArticleLikeEntity();
        entity2.setArticleId(dto.getArticleId());
        entity2.setStatus(LikeStatus.LIKE);
        entity2.setProfileId(prtId);
        articleLikeRepository.save(entity2);

        dto.setId(entity2.getId());
        dto.setCreatedDate(entity2.getCreatedDate());
        return dto;
    }

    public ArticleLikeDTO createDislike(ArticleLikeDTO dto, Integer prtId) {
        check(dto);
        ArticleLikeEntity entity = get(dto.getArticleId(), prtId);
        if (entity != null){
            delete(entity.getId());
        }
        ArticleLikeEntity entity2 = new ArticleLikeEntity();
        entity2.setArticleId(dto.getArticleId());
        entity2.setStatus(LikeStatus.LIKE);
        entity2.setProfileId(prtId);
        articleLikeRepository.save(entity2);

        dto.setId(entity2.getId());
        dto.setCreatedDate(entity2.getCreatedDate());
        return dto;
    }

    public Boolean delete(Integer id) {
        return articleLikeRepository.delete(id)==1;
    }

    public ArticleLikeEntity get(String articleId, Integer prtId ) {
        return articleLikeRepository.findByProfileIdAndArticleId(prtId, articleId);
    }

    private void check(ArticleLikeDTO dto) {
        if (dto.getArticleId().isBlank()){
            throw new AppBadRequestException("where is article ");
        }
    }

}
