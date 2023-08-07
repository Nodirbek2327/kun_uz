package com.example.service;

import com.example.dto.FilterDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.dto.RegionDTO;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CustomRepository;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CustomRepository customRepository;

    public ProfileDTO create(ProfileDTO dto, Integer prtId) {
        check(dto);
        Optional<ProfileEntity> profileByEmail = profileRepository.findByEmail(dto.getEmail());
        if (profileByEmail.isPresent()) {
            throw new AppBadRequestException("Email already exists");
        }
        Optional<ProfileEntity> profileByPhone = profileRepository.findByPhone(dto.getPhone());
        if (profileByPhone.isPresent()) {
            throw new AppBadRequestException("Phone already exits");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(dto.getRole());
        entity.setPrtId(prtId);
        profileRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

//    public Boolean update2(Integer profileId, ProfileDTO dto) {
//        check(dto); // check
//        ProfileEntity entity = get(profileId); // get
//        entity.setName(dto.getName());
//        entity.setSurname(dto.getSurname());
//        entity.setEmail(dto.getEmail());
//        entity.setPhone(dto.getPhone());
//        profileRepository.save(entity);
//        return true;
//    }

    public Boolean updateDetail(Integer profileId, ProfileDTO dto) {
        check(dto); // check
        int effectedRows = profileRepository.updateDetail(profileId, dto.getName(), dto.getSurname());
        return effectedRows == 1;
    }

    public ProfileEntity get(Integer profileId) {
        return profileRepository.findById(profileId).orElseThrow(() -> new AppBadRequestException("Profile not found"));
    }


    public ProfileDTO add(ProfileDTO dto) {
        dto.setStatus(ProfileStatus.ACTIVE);
        check(dto);
        ProfileEntity entity = toEntity(dto);
        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean update(Integer id, ProfileDTO profileDTO) {
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

    public Boolean delete(Integer id) {
        return profileRepository.delete(id)==1;
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
    }

    public ProfileDTO toDTO(ProfileEntity entity){
        ProfileDTO dto = new ProfileDTO();
       // dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
    //    dto.setPhoto_id(entity.getPhoto_id());
        dto.setVisible(entity.getVisible());
        dto.setPhone(entity.getPhone());
        dto.setPassword(entity.getPassword());
        return dto;
    }

    public ProfileEntity toEntity(ProfileDTO dto){
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(dto.getRole());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setStatus(dto.getStatus());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
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
