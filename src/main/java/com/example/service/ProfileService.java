package com.example.service;

import com.example.dto.FilterDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CustomRepository;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CustomRepository customRepository;

    public ProfileDTO add(ProfileDTO dto) {
        dto.setId(UUID.randomUUID());
        dto.setCreatedDate(LocalDateTime.now());
        check(dto);
        ProfileEntity entity = toEntity(dto);
        profileRepository.save(entity);
        return dto;
    }

    public Boolean update(UUID id, ProfileDTO profileDTO) {
        check(profileDTO);
        int effectedRows = profileRepository.updateAttribute(id,  toEntity(profileDTO));
        return effectedRows>0;
    }

    public PageImpl<ProfileDTO> profilePagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate"); //  sort qilishga pageablega berib yuboramiz
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProfileEntity> pageObj = profileRepository.findAll(pageable);
        return new PageImpl<>(getProfileDTOS(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }

    public Boolean delete(UUID id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        profileRepository.deleteById(id);
        return true;
    }

    public PageImpl<ProfileDTO> filter(ProfileFilterDTO filterDTO, int page, int size) {
        FilterDTO<ProfileEntity> result = customRepository.filterProfile(filterDTO, page, size);
        return new PageImpl<>(getProfileDTOS(result.getProfileEntityList()), PageRequest.of(page, size), result.getTotalCount());
    }


    private void check(ProfileDTO profileDTO) {
        if (profileDTO.getName() == null || profileDTO.getName().isBlank()) {
            throw new AppBadRequestException("Name qani?");
        }
        if (profileDTO.getSurname() == null || profileDTO.getSurname().isBlank()) {
            throw new AppBadRequestException("Surname qani?");
        }
        if (profileDTO.getPassword() == null || profileDTO.getPassword().isBlank()) {
            throw new AppBadRequestException("password qani?");
        }
        if ((profileDTO.getPhone() == null && profileDTO.getPhone().isBlank()) || (profileDTO.getEmail()==null && profileDTO.getEmail().isBlank())) {
            throw new AppBadRequestException("email yoki phone  qani?");
        }
    }

    public ProfileDTO toDTO(ProfileEntity entity){
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhoto_id(entity.getPhoto_id());
        dto.setVisible(entity.getVisible());
        dto.setPhone(entity.getPhone());
        dto.setPassword(entity.getPassword());
        return dto;
    }

    public ProfileEntity toEntity(ProfileDTO dto){
        ProfileEntity entity = new ProfileEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(dto.getRole());
        entity.setEmail(dto.getEmail());
        entity.setStatus(dto.getStatus());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setPhoto_id(dto.getPhoto_id());
        entity.setVisible(dto.getVisible());
        entity.setPhone(dto.getPhone());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    private List<ProfileDTO> getProfileDTOS(List<ProfileEntity> list) {
        if (list.isEmpty()) {
            throw  new ItemNotFoundException("profile not found");
        }
        List<ProfileDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }




}
