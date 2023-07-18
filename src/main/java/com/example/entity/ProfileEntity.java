package com.example.entity;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    @Column(nullable = false, unique = true)
    private String password;
    @Enumerated(value = EnumType.STRING)
    private ProfileStatus status;
    @Enumerated(value = EnumType.STRING)
    private ProfileRole role;
    private Boolean visible = true;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    private Integer prtId;
   // private Integer photo_id;
}
