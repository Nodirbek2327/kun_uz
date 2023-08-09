package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.TagDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.TagEntity;
import com.example.enums.Language;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ArticleTypeRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO createWithJwt(ArticleTypeDTO dto, Integer prtId) {
        check(dto);
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        entity.setPrtId(prtId);
        entity.setOrderNumber(dto.getOrderNumber());
        articleTypeRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean updateWithJwt(Integer id, ArticleTypeDTO dto) {
        check(dto); // check
        ArticleTypeEntity entity = get(id); // get
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameEng(dto.getNameEng());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        articleTypeRepository.save(entity);
        return true;
    }

    public ArticleTypeEntity get(Integer id) {
        return articleTypeRepository.findById(id).orElseThrow(() -> new AppBadRequestException("Profile not found"));
    }

    public Boolean delete(Integer id) {
        return articleTypeRepository.delete(id)==1;
    }


    public List<ArticleTypeDTO> getByLanguage(Language lang) {
        Iterable<ArticleTypeEntity> iterable = articleTypeRepository.findAllByVisibleIsTrue();
        List<ArticleTypeDTO> list = new LinkedList<>();
        switch (lang){
            case ru:{
                iterable.forEach(entity -> {
                    ArticleTypeDTO dto = new ArticleTypeDTO();
                    dto.setId(entity.getId());
                    dto.setOrderNumber(entity.getOrderNumber());
                    dto.setName(entity.getNameRu());
                    list.add(dto);
                });
            }
            case eng: {
                iterable.forEach(entity -> {
                    ArticleTypeDTO dto = new ArticleTypeDTO();
                    dto.setId(entity.getId());
                    dto.setOrderNumber(entity.getOrderNumber());
                    dto.setName(entity.getNameEng());
                    list.add(dto);
                });
            }
            default:{
                iterable.forEach(entity -> {
                    ArticleTypeDTO dto = new ArticleTypeDTO();
                    dto.setId(entity.getId());
                    dto.setOrderNumber(entity.getOrderNumber());
                    dto.setName(entity.getNameUz());
                    list.add(dto);
                });
            }
        }
        return list;
    }

    public PageImpl<ArticleTypeDTO> regionPagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "orderNumber"); //  sort qilishga pageablega berib yuboramiz
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ArticleTypeEntity> pageObj = articleTypeRepository.findAllByVisibleIsTrue(pageable);
        return new PageImpl<>(getArticleTypeDTOS(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }

    private void check(ArticleTypeDTO categoryDTO) {
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

    public ArticleTypeDTO toDTO(ArticleTypeEntity dto){
        ArticleTypeDTO entity = new ArticleTypeDTO();
        entity.setId(dto.getId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setVisible(dto.getVisible());
        entity.setNameEng(dto.getNameEng());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        entity.setOrderNumber(dto.getOrderNumber());
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
