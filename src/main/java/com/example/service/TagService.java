package com.example.service;

import com.example.dto.RegionDTO;
import com.example.dto.TagDTO;
import com.example.entity.RegionEntity;
import com.example.entity.TagEntity;
import com.example.enums.Language;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagDTO createWithJwt(TagDTO dto, Integer prtId) {
        check(dto);
        TagEntity entity = new TagEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        entity.setPrtId(prtId);
        entity.setOrderNumber(dto.getOrderNumber());
        tagRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean updateWithJwt(Integer id, TagDTO dto) {
        check(dto); // check
        TagEntity entity = get(id); // get
        entity.setNameEng(dto.getNameEng());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        tagRepository.save(entity);
        return true;
    }

    public TagEntity get(Integer id) {
        return tagRepository.findById(id).orElseThrow(() -> new AppBadRequestException("tag not found"));
    }

    public Boolean delete(Integer id) {
        return tagRepository.delete(id)==1;
    }

    public List<TagDTO> getByLanguage(Language lang) {
        Iterable<TagEntity> iterable = tagRepository.findAll();
        List<TagDTO> list = new LinkedList<>();
        switch (lang){
            case ru:{
                iterable.forEach(entity -> {
                    TagDTO dto = new TagDTO();
                    dto.setId(entity.getId());
                    dto.setOrderNumber(entity.getOrderNumber());
                    dto.setName(entity.getNameRu());
                    list.add(dto);
                });
            }
            case eng: {
                iterable.forEach(entity -> {
                    TagDTO dto = new TagDTO();
                    dto.setId(entity.getId());
                    dto.setOrderNumber(entity.getOrderNumber());
                    dto.setName(entity.getNameEng());
                    list.add(dto);
                });
            }
            default:{
                iterable.forEach(entity -> {
                    TagDTO dto = new TagDTO();
                    dto.setId(entity.getId());
                    dto.setOrderNumber(entity.getOrderNumber());
                    dto.setName(entity.getNameUz());
                    list.add(dto);
                });
            }
        }
        return list;
    }

    public PageImpl<TagDTO> regionPagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "order_number"); //  sort qilishga pageablega berib yuboramiz
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TagEntity> pageObj = tagRepository.findAll(pageable);
        return new PageImpl<>(getTagDTOS(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }

    private void check(TagDTO regionDTO) {
        if (regionDTO.getNameUz() == null || regionDTO.getNameUz().isBlank()) {
            throw new AppBadRequestException("uzbekcha Name qani?");
        }
        if (regionDTO.getNameRu() == null || regionDTO.getNameRu().isBlank()) {
            throw new AppBadRequestException("ruscha Name qani?");
        }
        if (regionDTO.getNameEng() == null || regionDTO.getNameEng().isBlank()) {
            throw new AppBadRequestException("englizcha Name qani?");
        }
        if (regionDTO.getOrderNumber() == null) {
            throw new AppBadRequestException("order_number Name qani?");
        }
    }

    public TagDTO toDTO(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setNameEng(entity.getNameEng());
        dto.setNameRu(entity.getNameRu());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setVisible(entity.getVisible());
        return dto;
    }

    private List<TagDTO> getTagDTOS(List<TagEntity> list) {
        if (list.isEmpty()) {
            throw new ItemNotFoundException("tag not found");
        }
        List<TagDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }

}
