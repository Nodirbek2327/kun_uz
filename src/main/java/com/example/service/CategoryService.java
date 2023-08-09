package com.example.service;

import com.example.dto.*;
import com.example.entity.CategoryEntity;
import com.example.entity.TagEntity;
import com.example.enums.Language;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO createWithJwt(CategoryDTO dto, Integer prtId) {
        check(dto);
        CategoryEntity entity = new CategoryEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        entity.setPrtId(prtId);
        entity.setOrderNumber(dto.getOrderNumber());
        categoryRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean updateWithJwt(Integer id, CategoryDTO dto) {
        check(dto); // check
        CategoryEntity entity = get(id); // get
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameEng(dto.getNameEng());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        categoryRepository.save(entity);
        return true;
    }

    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new AppBadRequestException("Profile not found"));
    }

    public Boolean delete(Integer id) {
        return categoryRepository.delete(id)==1;
    }

    public List<CategoryDTO> getByLanguage(Language lang) {
        Iterable<CategoryEntity> iterable = categoryRepository.findAllByVisibleIsTrue();
        List<CategoryDTO> list = new LinkedList<>();
        switch (lang){
            case ru:{
                iterable.forEach(entity -> {
                    CategoryDTO dto = new CategoryDTO();
                    dto.setId(entity.getId());
                    dto.setOrderNumber(entity.getOrderNumber());
                    dto.setName(entity.getNameRu());
                    list.add(dto);
                });
            }
            break;
            case eng: {
                iterable.forEach(entity -> {
                    CategoryDTO dto = new CategoryDTO();
                    dto.setId(entity.getId());
                    dto.setOrderNumber(entity.getOrderNumber());
                    dto.setName(entity.getNameEng());
                    list.add(dto);
                });
            }
            break;
            default:{
                iterable.forEach(entity -> {
                    CategoryDTO dto = new CategoryDTO();
                    dto.setId(entity.getId());
                    dto.setOrderNumber(entity.getOrderNumber());
                    dto.setName(entity.getNameUz());
                    list.add(dto);
                });
            }
        }
        return list;
    }

    public PageImpl<CategoryDTO> regionPagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "orderNumber"); //  sort qilishga pageablega berib yuboramiz
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CategoryEntity> pageObj = categoryRepository.findAllByVisibleIsTrue(pageable);
        return new PageImpl<>(getCategoryDTOS(pageObj.getContent()), pageable, pageObj.getTotalElements());
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
        if (categoryDTO.getOrderNumber() == null ) {
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
        entity.setOrderNumber(dto.getOrderNumber());
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
        entity.setOrderNumber(dto.getOrderNumber());
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
