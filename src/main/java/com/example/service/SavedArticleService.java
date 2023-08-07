package com.example.service;

import com.example.dto.SavedArticleDTO;
import com.example.entity.SavedArticleEntity;
import com.example.exp.AppBadRequestException;
import com.example.mapper.SavedArticleMapper;
import com.example.repository.SavedArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedArticleService {

    @Autowired
    private SavedArticleRepository savedArticleRepository;

    public SavedArticleDTO create(String articleId, Integer prtId) {
        check(articleId);
        SavedArticleDTO dto = new SavedArticleDTO();
        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(articleId);
        entity.setProfileId(prtId);
        savedArticleRepository.save(entity);

        dto.setArticleId(articleId);
        dto.setProfileId(prtId);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean delete(String articleId, Integer prtId) {
        return savedArticleRepository.deleteByArticleIdAndProfileId(articleId, prtId)==1;
    }

    public List<SavedArticleMapper> get(Integer prtId) {
        return savedArticleRepository.findAllByProfileId(prtId);
    }

    private void check(String articleId) {
        if (articleId==null){
            throw new AppBadRequestException("where is article id ");
        }
    }
}
