package com.example.service;

import com.example.dto.*;
import com.example.entity.ArticleEntity;
import com.example.entity.CommentEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import com.example.enums.Language;
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
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleTypesService articleTypesService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ArticleTagsService articleTagsService;
    @Autowired
    private CustomRepository customRepository;

    public ArticleDTO create(ArticleDTO dto, Integer moderatorId) {
        check(dto);
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setDescription(dto.getDescription());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setModeratorId(moderatorId);
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleTypesService.create(entity.getId(), dto.getArticleType()); // save type list
        articleTagsService.create(entity.getId(), dto.getArticleTag()); // save tag list
        articleRepository.save(entity); // save
        // response
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ArticleDTO update(String id, ArticleDTO dto, Integer moderatorId) {
        check(dto);
        ArticleEntity entity = get(id);
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setDescription(dto.getDescription());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(entity); // save

        articleTypesService.merge(entity.getId(), dto.getArticleType()); // save type list
        articleTagsService.merge(entity.getId(), dto.getArticleTag()); // save tag list
        // response
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ArticleEntity get(String id) {
        return articleRepository.findById(id).orElseThrow(() -> {
            throw new AppBadRequestException("Article not found");
        });
    }
    public Boolean delete(String id) {
        return articleRepository.delete(id)==1;
    }

    public Boolean changeStatus(String id) {
        return articleRepository.changeStatus(id, String.valueOf(ArticleStatus.PUBLISHED))==1;
    }

    public List<ArticleShortInfoMapper> getLast5ByTypes(Integer typeId) {
        return articleRepository.getLast5ArticleByArticleTypeIdNative(typeId, 5);
    }

    public List<ArticleShortInfoMapper> getLast3Types(Integer typeId) {
        return articleRepository.getLast5ArticleByArticleTypeIdNative(typeId, 3);
    }

    public List<ArticleShortInfoMapper> getLast8ExceptSome(List<String> excludedIds) {
        return articleRepository.getLast8ArticleExceptSome(excludedIds, ArticleStatus.PUBLISHED);
    }

    public ArticleDTO getById(String articleId, Language language) {
        ArticleEntity entity = get(articleId);
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageId(entity.getImageId());
        dto.setRegion(regionService.getByIdAndLanguage(entity.getRegionId(), language));
        return dto;
    }

    public List<ArticleShortInfoMapper> getLast4(Integer typeId, String id) {
        return articleRepository.getLast4ArticleByArticleTypeIdAndExcept(id, typeId, ArticleStatus.PUBLISHED);
    }

    public List<ArticleShortInfoMapper> mostRead4() {
        return articleRepository.get4MostRead(ArticleStatus.PUBLISHED);
    }

    public List<ArticleShortInfoMapper> getLast4ByTag(String articleId, Integer tagId) {
        return articleRepository.getLast4ArticleByArticleTagId(articleId, tagId, ArticleStatus.PUBLISHED);
    }

    public List<ArticleShortInfoMapper> getLast5ByTypeAndRegion(Integer typeId, Integer regionId) {
        return articleRepository.getLast5ArticleByArticleTypeIdAndRegionId(regionId, typeId, ArticleStatus.PUBLISHED);
    }

    public PageImpl<ArticleShortInfoMapper> getListByRegionPagination(Integer regionId, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> pageObj = articleRepository.getPaginationByRegionId(regionId, ArticleStatus.PUBLISHED, pageable);
        return new PageImpl<>(pageObj.getContent(), pageable, pageObj.getTotalElements());
    }

    public List<ArticleShortInfoMapper> getLast5ByCategory(Integer categoryId) {
        return articleRepository.getLast5ByCategoryId(categoryId, ArticleStatus.PUBLISHED);
    }


    public PageImpl<ArticleShortInfoMapper> getListByCategoryPagination(Integer categoryId, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> pageObj = articleRepository.getPaginationByCategoryId(categoryId, ArticleStatus.PUBLISHED, pageable);
        return new PageImpl<>(pageObj.getContent(), pageable, pageObj.getTotalElements());
    }

    public Boolean increaseViewCount(String id) {
        return articleRepository.increaseViewCount(id)==1;
    }

    public Boolean increaseSharedCount(String id) {
        return articleRepository.increaseSharedCount(id)==1;
    }

    public PageImpl<ArticleDTO> filter(ArticleFilterDTO filterDTO, int page, int size) {
        FilterResultDTO<ArticleEntity> result = customRepository.filterArticle(filterDTO, page, size);
        List<ArticleDTO> commentDTOList = result.getContent().stream()
                .map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(commentDTOList, PageRequest.of(page, size), result.getTotalCount());
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
//        if (articleDTO.getPublisherId() == null ) {
//            throw new AppBadRequestException("publisher  qani?");
//        }
        if (articleDTO.getRegionId() == null ) {
            throw new AppBadRequestException("region  qani?");
        }
    }

    public ArticleDTO toDTO(ArticleEntity entity){
        ArticleDTO dto = new ArticleDTO();
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setCategoryId(entity.getCategoryId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setTitle(entity.getTitle());
        dto.setRegionId(entity.getRegionId());
        return dto;
    }
//
//    public ArticleEntity toEntity(ArticleDTO dto){
//        ArticleEntity entity = new ArticleEntity();
//        entity.setDescription(dto.getDescription());
//        entity.setContent(dto.getContent());
//        entity.setCategoryId(dto.getCategoryId());
//        entity.setModeratorId(dto.getModeratorId());
//        entity.setStatus(dto.getStatus());
//        entity.setCreatedDate(dto.getCreatedDate());
//        entity.setVisible(dto.getVisible());
//        entity.setSharedCount(dto.getSharedCount());
//        entity.setTitle(dto.getTitle());
//        entity.setRegionId(dto.getRegionId());
//        entity.setViewCount(dto.getView_count());
//        entity.setPublishedDate(dto.getPublishedDate());
//        entity.setPublisherId(dto.getPublisherId());
//        return entity;
//    }


}
