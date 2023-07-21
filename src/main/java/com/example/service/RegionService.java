package com.example.service;

import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.RegionMapper;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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
        return regionRepository.findById(id).orElseThrow(() -> new AppBadRequestException("Profile not found"));
    }

    public Boolean delete(Integer id) {
       return regionRepository.delete(id)==1;
    }


    public PageImpl<RegionDTO> regionPagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "order_number"); //  sort qilishga pageablega berib yuboramiz
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RegionEntity> pageObj = regionRepository.findAll(pageable);
        return new PageImpl<>(getProfileDTOS(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }

//    public List<RegionDTO> getAll() {
//        Iterable<RegionEntity> iterable = regionRepository.findAll();
//        List<RegionDTO> dtoList = new LinkedList<>();
//        iterable.forEach(entity -> {
//            dtoList.add(toDTO(entity));
//        });
//        return dtoList;
//    }

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
        if (regionDTO.getOrder_number() == null) {
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
        dto.setOrder_number(entity.getOrder_number());
        dto.setVisible(entity.getVisible());
        return dto;
    }

    public RegionEntity toEntity(RegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setId(dto.getId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setNameEng(dto.getNameEng());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        entity.setOrder_number(dto.getOrder_number());
        entity.setVisible(dto.getVisible());
        return entity;
    }

    private List<RegionDTO> getProfileDTOS(List<RegionEntity> list) {
        if (list.isEmpty()) {
            throw new ItemNotFoundException("region not found");
        }
        List<RegionDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }


    public List<RegionMapper> getByLanguage(String lang) {
        Iterable<RegionEntity> iterable = regionRepository.findAll();
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

}