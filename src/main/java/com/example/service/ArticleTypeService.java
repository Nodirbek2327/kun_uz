package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.CategoryDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.CategoryEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.RegionMapper;
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
        articleTypeRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean updateWithJwt(Integer id, ArticleTypeDTO dto) {
        check(dto); // check
        ArticleTypeEntity entity = get(id); // get
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


    public PageImpl<ArticleTypeDTO> regionPagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "order_number"); //  sort qilishga pageablega berib yuboramiz
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ArticleTypeEntity> pageObj = articleTypeRepository.findAll(pageable);
        return new PageImpl<>(getArticleTypeDTOS(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }

    public List<RegionMapper> getByLanguage(String lang) {
        Iterable<ArticleTypeEntity> iterable = articleTypeRepository.findAll();
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
        if (categoryDTO.getOrder_number() == null ) {
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
        entity.setOrder_number(dto.getOrder_number());
        return entity;
    }

    public ArticleTypeEntity toEntity(ArticleTypeDTO dto){
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setId(dto.getId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setVisible(dto.getVisible());
        entity.setNameEng(dto.getNameEng());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
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
