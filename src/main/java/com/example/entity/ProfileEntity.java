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
public class ProfileEntity extends BaseEntity{
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

    @Column(name = "prt_id")
    private Integer prtId;

    @Column(name = "image_id", nullable = false)
    private String imageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private AttachEntity image;
}
