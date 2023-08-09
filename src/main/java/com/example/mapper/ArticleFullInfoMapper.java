package com.example.mapper;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.List;


public interface ArticleFullInfoMapper {
    String getId();
    String getTitle();
    String getDescription();
    String getContent();
    Long getShared_count();
    Integer getRegion_id();
    String getRegion_name();
    Integer getCategory_id();
    String getCategory_name();
    LocalDateTime getPublished_date();
    Long getView_count();
    Long getLike_count();
    String[] getTag_list();
}
