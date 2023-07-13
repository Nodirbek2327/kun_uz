package com.example.dto;

import com.example.entity.ProfileEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class FilterDTO<T> {
    private List<T> profileEntityList;
    private Long totalCount;

    public FilterDTO(List<T> profileEntityList, Long totalCount) {
        this.profileEntityList = profileEntityList;
        this.totalCount = totalCount;
    }
}
