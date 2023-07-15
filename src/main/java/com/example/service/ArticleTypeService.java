package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.CategoryDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.CategoryEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.RegionMapper;
import com.example.repository.ArticleTypeRepository;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Setter
@Getter
@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO add(ArticleTypeDTO dto) {
        dto.setId(UUID.randomUUID());
        dto.setCreatedDate(LocalDateTime.now());
        check(dto);
        ArticleTypeEntity entity = toEntity(dto);
        articleTypeRepository.save(entity);
        return dto;
    }

    public Boolean update(UUID id, ArticleTypeDTO categoryDTO) {
        check(categoryDTO);
        int effectedRows = articleTypeRepository.updateAttribute(id, toEntity(categoryDTO));
        return effectedRows > 0;
    }

    public Boolean delete(UUID id) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        articleTypeRepository.deleteById(id);
        return true;
    }

    public List<ArticleTypeDTO> getAll() {
        Iterable<ArticleTypeEntity> iterable = articleTypeRepository.findAll();
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        iterable.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }

    public List<RegionMapper> getByLanguage(String lang) {
        Iterable<ArticleTypeEntity> iterable = articleTypeRepository.findAll();
        List<RegionMapper> list = new LinkedList<>();
        if (lang.startsWith("ru")) {
            iterable.forEach(regionEntity -> {
                RegionMapper regionMapper = new RegionMapper();
                regionMapper.setId(regionEntity.getId());
                regionMapper.setOrder_number(regionEntity.getOrder_number());
                regionMapper.setName(regionEntity.getName_ru());
                list.add(regionMapper);
            });
        } else if (lang.startsWith("eng")) {
            iterable.forEach(regionEntity -> {
                RegionMapper regionMapper = new RegionMapper();
                regionMapper.setId(regionEntity.getId());
                regionMapper.setOrder_number(regionEntity.getOrder_number());
                regionMapper.setName(regionEntity.getName_eng());
                list.add(regionMapper);
            });
        } else if (lang.startsWith("uz")) {
            iterable.forEach(regionEntity -> {
                RegionMapper regionMapper = new RegionMapper();
                regionMapper.setId(regionEntity.getId());
                regionMapper.setOrder_number(regionEntity.getOrder_number());
                regionMapper.setName(regionEntity.getName_uz());
                list.add(regionMapper);
            });
        } else {
            throw new AppBadRequestException("mazgi bunday language yo'q");
        }
        return list;
    }

    private void check(ArticleTypeDTO categoryDTO) {
        if (categoryDTO.getName_uz() == null || categoryDTO.getName_uz().isBlank()) {
            throw new AppBadRequestException("uzbekcha Name qani?");
        }
        if (categoryDTO.getName_ru() == null || categoryDTO.getName_ru().isBlank()) {
            throw new AppBadRequestException("ruscha Name qani?");
        }
        if (categoryDTO.getName_eng() == null || categoryDTO.getName_eng().isBlank()) {
            throw new AppBadRequestException("englizcha Name qani?");
        }
        if (categoryDTO.getOrder_number() == null ) {
            throw new AppBadRequestException("order_number Name qani?");
        }
    }

    public ArticleTypeDTO toDTO(ArticleTypeEntity dto){
        ArticleTypeDTO entity = new ArticleTypeDTO();
        entity.setId(dto.getId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setVisible(dto.getVisible());
        entity.setName_eng(dto.getName_eng());
        entity.setName_ru(dto.getName_ru());
        entity.setName_uz(dto.getName_uz());
        entity.setOrder_number(dto.getOrder_number());
        return entity;
    }

    public ArticleTypeEntity toEntity(ArticleTypeDTO dto){
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setId(dto.getId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setVisible(dto.getVisible());
        entity.setName_eng(dto.getName_eng());
        entity.setName_ru(dto.getName_ru());
        entity.setName_uz(dto.getName_uz());
        entity.setOrder_number(dto.getOrder_number());
        return entity;
    }

    private List<ArticleTypeDTO> getArticleTypeDTOS(List<ArticleTypeEntity> list) {
        if (list.isEmpty()) {
            throw  new ItemNotFoundException("articleType not found");
        }
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }

}
