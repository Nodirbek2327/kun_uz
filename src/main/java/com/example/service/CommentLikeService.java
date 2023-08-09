package com.example.service;

import com.example.dto.CommentLikeDTO;
import com.example.entity.CommentLikeEntity;
import com.example.enums.LikeStatus;
import com.example.exp.AppBadRequestException;
import com.example.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;

    public CommentLikeDTO createLike(CommentLikeDTO dto, Integer prtId) {
        check(dto);
        CommentLikeEntity commentLikeEntity = get(dto.getArticleId(), dto.getCommentId(), prtId);
        if ( commentLikeEntity != null){
            delete(commentLikeEntity.getId());
        }
        CommentLikeEntity entity = new CommentLikeEntity();
        entity.setCommentId(dto.getCommentId());
        entity.setArticleId(dto.getArticleId());
        entity.setStatus(LikeStatus.LIKE);
        entity.setProfileId(prtId);
        commentLikeRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CommentLikeDTO createDislike(CommentLikeDTO dto, Integer prtId) {
        check(dto);
        CommentLikeEntity commentLikeEntity = get(dto.getArticleId(), dto.getCommentId(), prtId);
        if ( commentLikeEntity != null){
            delete(commentLikeEntity.getId());
        }
        CommentLikeEntity entity = new CommentLikeEntity();
        entity.setCommentId(dto.getCommentId());
        entity.setArticleId(dto.getArticleId());
        entity.setStatus(LikeStatus.DISLIKE);
        entity.setProfileId(prtId);
        commentLikeRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean delete(String id) {
        return commentLikeRepository.delete(id)==1;
    }

    public CommentLikeEntity get(String articleId, String commentId, Integer prtId ) {
        return commentLikeRepository.findByProfileIdAndCommentIdOrArticleId(prtId, commentId, articleId);
    }

    private void check(CommentLikeDTO dto) {
        if (dto.getArticleId().isBlank() || dto.getCommentId().isBlank()){
            throw new AppBadRequestException("where is article or  comment ");
        }
    }
}
