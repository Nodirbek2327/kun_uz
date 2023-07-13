package com.example.service;

import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.RegionMapper;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO add(RegionDTO dto) {
        dto.setId(UUID.randomUUID());
        dto.setCreatedDate(LocalDateTime.now());
        check(dto);
        RegionEntity entity = toEntity(dto);
        regionRepository.save(entity);
        return dto;
    }

    public Boolean update(UUID id, RegionDTO profileDTO) {
        check(profileDTO);
        int effectedRows = regionRepository.updateAttribute(id, toEntity(profileDTO));
        return effectedRows > 0;
    }

    public Boolean delete(UUID id) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        regionRepository.deleteById(id);
        return true;
    }

    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> iterable = regionRepository.findAll();
        List<RegionDTO> dtoList = new LinkedList<>();
        iterable.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }

    private void check(RegionDTO regionDTO) {
        if (regionDTO.getName_uz() == null || regionDTO.getName_uz().isBlank()) {
            throw new AppBadRequestException("uzbekcha Name qani?");
        }
        if (regionDTO.getName_ru() == null || regionDTO.getName_ru().isBlank()) {
            throw new AppBadRequestException("ruscha Name qani?");
        }
        if (regionDTO.getName_eng() == null || regionDTO.getName_eng().isBlank()) {
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
        dto.setName_eng(entity.getName_eng());
        dto.setName_ru(entity.getName_ru());
        dto.setName_uz(entity.getName_uz());
        dto.setOrder_number(entity.getOrder_number());
        dto.setVisible(entity.getVisible());
        return dto;
    }

    public RegionEntity toEntity(RegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setId(dto.getId());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setName_eng(dto.getName_eng());
        entity.setName_ru(dto.getName_ru());
        entity.setName_uz(dto.getName_uz());
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

}