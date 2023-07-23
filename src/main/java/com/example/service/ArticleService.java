package com.example.service;

import com.example.dto.*;
import com.example.entity.ArticleEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import com.example.exp.AppBadRequestException;
import com.example.mapper.ArticleShortInfoMapper;
import com.example.repository.ArticleRepository;
import com.example.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Boolean update(String id, ArticleDTO articleDTO) {
        check(articleDTO);
        int effectedRows = articleRepository.updateAttribute(id,  toEntity(articleDTO));
        return effectedRows>0;
    }

    public Boolean delete(String id) {
        return articleRepository.delete(id)==1;
    }

    public Boolean changeStatus(String id) {
        return articleRepository.changeStatus(id, String.valueOf(ArticleStatus.Published))==1;
    }

    public List<ArticleShortInfoMapper> getLast5(Integer typeId) {
        return articleRepository.getLast5(typeId);
    }

    public List<ArticleShortInfoMapper> getLast3(Integer typeId) {
        return articleRepository.getLast3(typeId);
    }

    public List<ArticleShortInfoMapper> getLast8(List<String> excludedIds) {
        return articleRepository.getLast8(excludedIds);
    }

    public List<ArticleShortInfoMapper> getLast4(Integer typeId, String id) {
        return articleRepository.getLast4(typeId, id);
    }

    public List<ArticleShortInfoMapper> mostRead4() {
        return articleRepository.mostRead4();
    }

    public List<ArticleShortInfoMapper> getLast4ByTag(String tagName) {
        return articleRepository.getLast4ByTag(tagName);
    }

    public List<ArticleShortInfoMapper> getLast5ByTypeAndRegion(String type, Integer regionId) {
        return articleRepository.getLast5ByTypeAndRegion(regionId, type);
    }

    public PageImpl<ArticleShortInfoMapper> getListByRegionPagination(Integer regionId, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> pageObj = articleRepository.getListRegionPagination(regionId, pageable);
        return new PageImpl<>(pageObj.getContent(), pageable, pageObj.getTotalElements());
    }

    public List<ArticleShortInfoMapper> getLast5ByCategory(Integer id) {
        return articleRepository.getLast5ByCategory(id);
    }


    public PageImpl<ArticleShortInfoMapper> getListByCategoryPagination(Integer categoryId, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> pageObj = articleRepository.getListCategoryPagination(categoryId, pageable);
        return new PageImpl<>(pageObj.getContent(), pageable, pageObj.getTotalElements());
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


}
