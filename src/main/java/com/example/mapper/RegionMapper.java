package com.example.mapper;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class RegionMapper {
    private UUID id;
    private Integer order_number;
    private String name;
}
