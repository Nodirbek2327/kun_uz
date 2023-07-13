package com.example.dto;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String phone;
    private String role;
    private LocalDate created_date_from;
    private LocalDate created_date_to;
}
