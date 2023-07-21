package com.example.service;

import com.example.dto.ArticleDTO;
import com.example.dto.FilterDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ArticleRepository;
import com.example.repository.CustomRepository;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CustomRepository customRepository;

    public ArticleDTO create(ArticleDTO dto, Integer prtId) {
        check(dto);
        Optional<ArticleEntity> articleTitle = articleRepository.findByTitle(dto.getTitle());
        if (articleTitle.isPresent()) {
            throw new AppBadRequestException("title already exists");
        }

        ArticleEntity entity = new ArticleEntity();
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setTitle(dto.getTitle());
        entity.setSharedCount(dto.getSharedCount());
        entity.setRegionId(dto.getRegionId());
        entity.setStatus(ArticleStatus.NotPublished);
        entity.setCategoryId(dto.getCategoryId());
        entity.setPrtId(prtId);
        articleRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(UUID id, ArticleDTO articleDTO) {
        check(articleDTO);
        int effectedRows = articleRepository.updateAttribute(id,  toEntity(articleDTO));
        return effectedRows>0;
    }

    public Boolean delete(UUID id) {
        return articleRepository.delete(id)==1;
    }

    public Boolean changeStatus(UUID id) {
        return articleRepository.changeStatus(id, String.valueOf(ArticleStatus.Published))==1;
    }

    public List<ArticleDTO> getLast5() {
        List<ArticleEntity> entityList = articleRepository.getTop5();
        return getArticleDTOS(entityList);
    }

    public List<ArticleDTO> getLast3() {
        List<ArticleEntity> entityList = articleRepository.getTop3();
        return getArticleDTOS(entityList);
    }

    public List<ArticleDTO> getLast8(List<UUID> excludedIds) {
        List<ArticleEntity> entityList = articleRepository.getLast8(excludedIds);
        return getArticleDTOS(entityList);
    }

    public List<ArticleDTO> getLast4(UUID id) {
        List<ArticleEntity> entityList = articleRepository.getLast4(id);
        return getArticleDTOS(entityList);
    }


    public List<ArticleDTO> mostRead4() {
        List<ArticleEntity> entityList = articleRepository.mostRead4();
        return getArticleDTOS(entityList);
    }


    private void check(ArticleDTO articleDTO) {
        if (articleDTO.getCategoryId() == null) {
            throw new AppBadRequestException("category qani?");
        }
        if (articleDTO.getContent() == null || articleDTO.getContent().isBlank()) {
            throw new AppBadRequestException("content qani?");
        }
        if (articleDTO.getDescription() == null || articleDTO.getDescription().isBlank()) {
            throw new AppBadRequestException("description qani?");
        }
        if (articleDTO.getTitle() == null && articleDTO.getTitle().isBlank()) {
            throw new AppBadRequestException("title  qani?");
        }
        if (articleDTO.getPublisherId() == null ) {
            throw new AppBadRequestException("publisher  qani?");
        }
        if (articleDTO.getRegionId() == null ) {
            throw new AppBadRequestException("region  qani?");
        }
    }

    public ArticleDTO toDTO(ArticleEntity entity){
        ArticleDTO dto = new ArticleDTO();
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setCategoryId(entity.getCategoryId());
        dto.setModeratorId(entity.getModeratorId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.getVisible());
        dto.setSharedCount(entity.getSharedCount());
        dto.setTitle(entity.getTitle());
        dto.setRegionId(entity.getRegionId());
        dto.setView_count(entity.getViewCount());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setPublisherId(entity.getPublisherId());
        return dto;
    }

    public ArticleEntity toEntity(ArticleDTO dto){
        ArticleEntity entity = new ArticleEntity();
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setCategoryId(dto.getCategoryId());
        entity.setModeratorId(dto.getModeratorId());
        entity.setStatus(dto.getStatus());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setVisible(dto.getVisible());
        entity.setSharedCount(dto.getSharedCount());
        entity.setTitle(dto.getTitle());
        entity.setRegionId(dto.getRegionId());
        entity.setViewCount(dto.getView_count());
        entity.setPublishedDate(dto.getPublishedDate());
        entity.setPublisherId(dto.getPublisherId());
        return entity;
    }

    private List<ArticleDTO> getArticleDTOS(List<ArticleEntity> list) {
        if (list.isEmpty()) {
            throw  new ItemNotFoundException("article not found");
        }
        List<ArticleDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }


}
