package com.example.service;

import com.example.dto.CommentDTO;
import com.example.dto.CommentFilterDTO;
import com.example.dto.FilterResultDTO;
import com.example.entity.CommentEntity;
import com.example.exp.AppBadRequestException;
import com.example.mapper.CommentMapper;
import com.example.repository.CommentRepository;
import com.example.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CustomRepository customRepository;

    public CommentDTO create(CommentDTO dto, Integer profileId) {
        check(dto);
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setProfileId(profileId);
        entity.setArticleId(dto.getArticleId());
        commentRepository.save(entity); // save
        // response
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CommentDTO update(String id, CommentDTO dto, Integer profileId) {
        check(dto);
        CommentEntity entity = get(id);
        entity.setContent(dto.getContent());
        entity.setUpdateDate(LocalDateTime.now());
        entity.setProfileId(profileId);
        entity.setArticleId(dto.getArticleId());
        commentRepository.save(entity); // save
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean delete(String id) {
        return commentRepository.delete(id)==1;
    }

    public CommentEntity get(String id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new AppBadRequestException("comment not found");
        });
    }

    public List<CommentMapper> getList(String articleId) {
        List<CommentMapper> list = new LinkedList<>();
         commentRepository.findByArticleId(articleId).stream().forEach(commentEntity ->{
            CommentMapper commentMapper = new CommentMapper();
            commentMapper.setProfileId(commentEntity.getProfile().getId());
            commentMapper.setUpdateDate(commentEntity.getUpdateDate());
            commentMapper.setProfileName(commentEntity.getProfile().getName());
            commentMapper.setProfileSurname(commentEntity.getProfile().getSurname());
            commentMapper.setCreatedDate(commentEntity.getCreatedDate());
            commentMapper.setId(commentEntity.getId());
            list.add(commentMapper);
        });
         return list;
    }

    public PageImpl<CommentEntity> getListPagination( int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        PageImpl<CommentEntity> pageObj = commentRepository.getPagination( pageable);
        return new PageImpl<>(pageObj.getContent(), pageable, pageObj.getTotalElements());
    }

    public PageImpl<CommentDTO> filter(CommentFilterDTO filterDTO, int page, int size) {
        FilterResultDTO<CommentEntity> result = customRepository.filterComment(filterDTO, page, size);
        List<CommentDTO> commentDTOList = result.getContent().stream()
                .map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(commentDTOList, PageRequest.of(page, size), result.getTotalCount());
    }


    private void check(CommentDTO dto) {
        if (dto.getArticleId() == null || dto.getArticleId().isBlank()) {
            throw new AppBadRequestException("articleId qani?");
        }
        if (dto.getContent() == null || dto.getContent().isBlank()) {
            throw new AppBadRequestException("content qani?");
        }
    }

    public CommentDTO toDTO(CommentEntity entity){
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setContent(entity.getContent());
        dto.setProfileId(entity.getProfileId());
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setArticleId(entity.getArticleId());
        return dto;
    }
}
