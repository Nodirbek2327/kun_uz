package com.example.entity;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "profile")
public class ProfileEntity {
    @Id
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    @Column(nullable = false)
    private String password;
    @Enumerated(value = EnumType.STRING)
    private ProfileStatus status;
    @Enumerated(value = EnumType.STRING)
    private ProfileRole role;
    private Boolean visible;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    private Integer photo_id;
}
