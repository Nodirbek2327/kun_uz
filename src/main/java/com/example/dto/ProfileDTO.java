package com.example.dto;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
    private Boolean visible;
    private LocalDateTime createdDate;
    private String jwt;
   // private Integer photo_id;
}
