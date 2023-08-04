package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, String> {

    List<EmailHistoryEntity> findAllByEmail(String email);

    List<EmailHistoryEntity> findAllByCreatedDateBetween(LocalDateTime localDateTime1, LocalDateTime localDateTime2);

    PageImpl<EmailHistoryEntity> findAll(Pageable pageable);

    @Query("from EmailHistoryEntity as e where e.email=:email and e.createdDate between :date1 and :date2")
    List<EmailHistoryEntity> findAllByEmailAndDates(@Param("email") String toAccount, @Param("date1") LocalDateTime localDateTime, @Param("date2") LocalDateTime now);
}
