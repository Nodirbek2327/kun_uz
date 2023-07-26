package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "tag")
public class TagEntity extends BaseEntity{
    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;
    @Column(name = "name_uz", nullable = false)
    private String nameUz;
    @Column(name = "name_ru", nullable = false)
    private String nameRu;
    @Column(name = "name_eng", nullable = false)
    private String nameEng;
    @Column(name = "prt_id")
    private Integer prtId;
}
