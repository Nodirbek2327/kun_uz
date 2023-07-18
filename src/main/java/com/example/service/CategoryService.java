package com.example.service;

import com.example.dto.*;
import com.example.entity.CategoryEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.RegionMapper;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    public CategoryDTO add(CategoryDTO dto) {
        //dto.setCreatedDate(LocalDateTime.now());
        check(dto);
        CategoryEntity entity = toEntity(dto);
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean update(Integer id, CategoryDTO categoryDTO) {
        check(categoryDTO);
        int effectedRows = categoryRepository.updateAttribute(id, toEntity(categoryDTO));
        return effectedRows > 0;
    }

    public Boolean delete(Integer id) {
        return  categoryRepository.delete(id)==1;
    }

    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> iterable = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new LinkedList<>();
        iterable.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }

    public List<RegionMapper> getByLanguage(String lang) {
        Iterable<CategoryEntity> iterable = categoryRepository.findAll();
        List<RegionMapper> list = new LinkedList<>();
        if (lang.startsWith("ru")) {
            iterable.forEach(regionEntity -> {
                RegionMapper regionMapper = new RegionMapper();
                regionMapper.setId(regionEntity.getId());
                regionMapper.setOrder_number(regionEntity.getOrder_number());
                regionMapper.setName(regionEntity.getNameRu());
                list.add(regionMapper);
            });
        } else if (lang.startsWith("eng")) {
            iterable.forEach(regionEntity -> {
                RegionMapper regionMapper = new RegionMapper();
                regionMapper.setId(regionEntity.getId());
                regionMapper.setOrder_number(regionEntity.getOrder_number());
                regionMapper.setName(regionEntity.getNameEng());
                list.add(regionMapper);
            });
        } else if (lang.startsWith("uz")) {
            iterable.forEach(regionEntity -> {
                RegionMapper regionMapper = new RegionMapper();
                regionMapper.setId(regionEntity.getId());
                regionMapper.setOrder_number(regionEntity.getOrder_number());
                regionMapper.setName(regionEntity.getNameUz());
                list.add(regionMapper);
            });
        } else {
            throw new AppBadRequestException("mazgi bunday language yo'q");
        }
        return list;
    }

    private void check(CategoryDTO categoryDTO) {
        if (categoryDTO.getNameUz() == null || categoryDTO.getNameUz().isBlank()) {
            throw new AppBadRequestException("uzbekcha Name qani?");
        }
        if (categoryDTO.getNameRu() == null || categoryDTO.getNameRu().isBlank()) {
            throw new AppBadRequestException("ruscha Name qani?");
        }
        if (categoryDTO.getNameEng() == null || categoryDTO.getNameEng().isBlank()) {
            throw new AppBadRequestException("englizcha Name qani?");
        }
        if (categoryDTO.getOrder_number() == null ) {
            throw new AppBadRequestException("order_number Name qani?");
        }
    }

    public CategoryDTO toDTO(CategoryEntity dto){
        CategoryDTO entity = new CategoryDTO();
        entity.setId(dto.getId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setVisible(dto.getVisible());
        entity.setNameEng(dto.getNameEng());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        entity.setOrder_number(dto.getOrder_number());
        return entity;
    }

    public CategoryEntity toEntity(CategoryDTO dto){
        CategoryEntity entity = new CategoryEntity();
        entity.setId(dto.getId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setVisible(dto.getVisible());
        entity.setNameEng(dto.getNameEng());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        entity.setOrder_number(dto.getOrder_number());
        return entity;
    }

    private List<CategoryDTO> getCategoryDTOS(List<CategoryEntity> list) {
        if (list.isEmpty()) {
            throw  new ItemNotFoundException("category not found");
        }
        List<CategoryDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }



}
