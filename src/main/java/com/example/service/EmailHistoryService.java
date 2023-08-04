package com.example.service;

import com.example.dto.EmailHistoryDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public List<EmailHistoryDTO> getEmailHistory(String email) {
        List<EmailHistoryEntity> entityList = emailHistoryRepository.findAllByEmail(email);
        if (entityList.isEmpty()){
            throw new AppBadRequestException("there no any emails");
        }
        List<EmailHistoryDTO> dtoList = new LinkedList<>();
        entityList.forEach(emailHistoryEntity -> {
            dtoList.add(toDTO(emailHistoryEntity));
        });
        return dtoList;
    }

    public List<EmailHistoryDTO> getEmailByDates(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
      List<EmailHistoryEntity> entityList = emailHistoryRepository.findAllByCreatedDateBetween(localDateTime1, localDateTime2);
      if (entityList.isEmpty()){
          throw new AppBadRequestException("there no any emails");
      }
      List<EmailHistoryDTO> dtoList = new LinkedList<>();
      entityList.forEach(emailHistoryEntity -> {
          dtoList.add(toDTO(emailHistoryEntity));
      });
      return dtoList;
    }

    private EmailHistoryDTO toDTO(EmailHistoryEntity entity){
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setEmail(entity.getEmail());
        return dto;
    }

    public PageImpl<EmailHistoryDTO> emailHistoryPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageImpl<EmailHistoryEntity> pageObj = emailHistoryRepository.findAll(pageable);
        return new PageImpl<>(getEmailHistoryDTOS(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }


    private List<EmailHistoryDTO> getEmailHistoryDTOS(List<EmailHistoryEntity> list) {
        if (list.isEmpty()) {
            throw new ItemNotFoundException("email not found");
        }
        List<EmailHistoryDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }
}

