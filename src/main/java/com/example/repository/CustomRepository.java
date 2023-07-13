package com.example.repository;

import com.example.dto.FilterDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomRepository {
     @Autowired
     private EntityManager entityManager;
    public FilterDTO<ProfileEntity> filterProfile(ProfileFilterDTO filterDTO, int page, int size) {

        StringBuilder stringBuilder = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        if (filterDTO.getName() != null) {
            stringBuilder.append(" and s.name =:name");
            params.put("name", filterDTO.getName());
        }
        if (filterDTO.getSurname() != null) {
            stringBuilder.append(" and s.surname =:surname");
            params.put("surname", filterDTO.getSurname());
        }
        if (filterDTO.getRole() != null) {
            stringBuilder.append(" and s.role =:role");
            params.put("role", filterDTO.getRole());
        }
        if (filterDTO.getPhone() != null) {
            stringBuilder.append(" and s.phone =:phone");
            params.put("phone", filterDTO.getPhone());
        }
        if (filterDTO.getCreated_date_from() != null && filterDTO.getCreated_date_to() != null) {
            stringBuilder.append(" and s.createdDate between :dateFrom and :dateTo ");
            params.put("dateFrom", LocalDateTime.of(filterDTO.getCreated_date_from(), LocalTime.MIN));
            params.put("dateTo", LocalDateTime.of(filterDTO.getCreated_date_to(), LocalTime.MAX));
        } else if (filterDTO.getCreated_date_from() != null) {
            stringBuilder.append(" and s.createdDate >= :dateFrom");
            params.put("dateFrom", LocalDateTime.of(filterDTO.getCreated_date_from(), LocalTime.MIN));
        } else if (filterDTO.getCreated_date_to() != null) {
            stringBuilder.append(" and s.createdDate <= :dateTo");
            params.put("dateFrom", LocalDateTime.of(filterDTO.getCreated_date_to(), LocalTime.MAX));
        }

        StringBuilder selectBuilder = new StringBuilder("select s from ProfileEntity as s where s.visible = true ");
        selectBuilder.append(stringBuilder);
        selectBuilder.append(" order by createdDate desc");

        StringBuilder countBuilder = new StringBuilder("select count(s) from StudentEntity as s where s.visible=true");
        countBuilder.append(stringBuilder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size); // limit
        selectQuery.setFirstResult(size * page); // offset

        Query countQuery = entityManager.createQuery(countBuilder.toString());
        // params
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<ProfileEntity> entityList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return  new FilterDTO<>(entityList, totalCount);
    }

//
//    public List<StudentMarkEntity> filterStudentMark(StudentMarkFilterDTO filterDTO, int page, int size) {
//
//        StringBuilder stringBuilder = new StringBuilder("select s from StudentMarkEntity where 1=1");
//        Map<String, Object> params = new HashMap<>();
//        if (filterDTO.getMark() != null) {
//            stringBuilder.append(" and s.mark =:mark");
//            params.put("mark", filterDTO.getMark());
//        }
//        if (filterDTO.getStudentId().getId() != null) {
//            stringBuilder.append(" and s.studentId =:studentId");
//            params.put("studentId", filterDTO.getStudentId().getId());
//        }
//        if (filterDTO.getId() != null) {
//            stringBuilder.append(" and s.id =:id");
//            params.put("id", filterDTO.getId());
//        }
//        if (filterDTO.getCreatedDate() != null) {
//            stringBuilder.append(" and s.createdDate =:date");
//            params.put("date", filterDTO.getCreatedDate());
//        }
//        if (filterDTO.getCourseId() != null) {
//            stringBuilder.append(" and s.courseId =:courseId");
//            params.put("courseId", filterDTO.getCourseId().getId());
//        }
//
//        stringBuilder.append(" order by cratedDate");
//
//        Query query = entityManager.createQuery(stringBuilder.toString());
//        // params
//        for (Map.Entry<String, Object> param : params.entrySet()) {
//            query.setParameter(param.getKey(), param.getValue());
//        }
//
//        List<StudentMarkEntity> entityList = query.getResultList();
//
//        return entityList;
//    }
//
//    public List<CourseEntity> filterCourse(CourseFilterDTO filterDTO, int page, int size) {
//
//        StringBuilder stringBuilder = new StringBuilder("select s from CourseEntity where 1=1");
//        Map<String, Object> params = new HashMap<>();
//        if (filterDTO.getCreatedDate() != null) {
//            stringBuilder.append(" and s.createdDate =:date");
//            params.put("date", filterDTO.getCreatedDate());
//        }
//        if (filterDTO.getName()!= null) {
//            stringBuilder.append(" and s.name =:name");
//            params.put("name", filterDTO.getName());
//        }
//        if (filterDTO.getId() != null) {
//            stringBuilder.append(" and s.id =:id");
//            params.put("id", filterDTO.getId());
//        }
//        if (filterDTO.getDuration() != null) {
//            stringBuilder.append(" and s.duration =:time");
//            params.put("time", filterDTO.getDuration());
//        }
//        if (filterDTO.getPrice() != null) {
//            stringBuilder.append(" and s.price =:price");
//            params.put("price", filterDTO.getPrice());
//        }
//
//        stringBuilder.append(" order by cratedDate");
//
//        Query query = entityManager.createQuery(stringBuilder.toString());
//        // params
//        for (Map.Entry<String, Object> param : params.entrySet()) {
//            query.setParameter(param.getKey(), param.getValue());
//        }
//
//        List<CourseEntity> entityList = query.getResultList();
//
//        return entityList;
//    }
}
