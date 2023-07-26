package com.example.service;

import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.Language;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;


    public RegionDTO createWithJwt(RegionDTO dto, Integer prtId) {
        check(dto);
        RegionEntity entity = new RegionEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        entity.setPrtId(prtId);
        entity.setOrderNumber(dto.getOrderNumber());
        regionRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean updateWithJwt(Integer id, RegionDTO dto) {
        check(dto); // check
        RegionEntity entity = get(id); // get
        entity.setNameEng(dto.getNameEng());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        regionRepository.save(entity);
        return true;
    }

    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> new AppBadRequestException("region not found"));
    }

    public Boolean delete(Integer id) {
       return regionRepository.delete(id)==1;
    }

    public List<RegionDTO> getByLanguage(Language lang) {
        Iterable<RegionEntity> iterable = regionRepository.findAll();
        List<RegionDTO> list = new LinkedList<>();
        switch (lang){
            case ru:{
                iterable.forEach(regionEntity -> {
                    RegionDTO dto = new RegionDTO();
                    dto.setId(regionEntity.getId());
                    dto.setOrderNumber(regionEntity.getOrderNumber());
                    dto.setName(regionEntity.getNameRu());
                    list.add(dto);
                });
            }
            case eng: {
                iterable.forEach(regionEntity -> {
                    RegionDTO dto = new RegionDTO();
                    dto.setId(regionEntity.getId());
                    dto.setOrderNumber(regionEntity.getOrderNumber());
                    dto.setName(regionEntity.getNameEng());
                    list.add(dto);
                });
            }
            default:{
                iterable.forEach(regionEntity -> {
                    RegionDTO dto = new RegionDTO();
                    dto.setId(regionEntity.getId());
                    dto.setOrderNumber(regionEntity.getOrderNumber());
                    dto.setName(regionEntity.getNameUz());
                    list.add(dto);
                });
            }
        }
        return list;
    }

    public RegionDTO getByIdAndLanguage(Integer id, Language lang) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        RegionEntity entity = optional.get();
        RegionDTO dto = new RegionDTO();
        switch (lang){
            case ru:{
                    dto.setId(entity.getId());
                    dto.setOrderNumber(entity.getOrderNumber());
                    dto.setName(entity.getNameRu());
            }
            case eng: {
                dto.setId(entity.getId());
                dto.setOrderNumber(entity.getOrderNumber());
                dto.setName(entity.getNameEng());
            }
            default:{
                dto.setId(entity.getId());
                dto.setOrderNumber(entity.getOrderNumber());
                dto.setName(entity.getNameUz());
            }
        }
        return dto;
    }

    public PageImpl<RegionDTO> regionPagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "order_number"); //  sort qilishga pageablega berib yuboramiz
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RegionEntity> pageObj = regionRepository.findAll(pageable);
        return new PageImpl<>(getRegionDTOS(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }

    private void check(RegionDTO regionDTO) {
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

    public RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setNameEng(entity.getNameEng());
        dto.setNameRu(entity.getNameRu());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setVisible(entity.getVisible());
        return dto;
    }

    private List<RegionDTO> getRegionDTOS(List<RegionEntity> list) {
        if (list.isEmpty()) {
            throw new ItemNotFoundException("region not found");
        }
        List<RegionDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }
}