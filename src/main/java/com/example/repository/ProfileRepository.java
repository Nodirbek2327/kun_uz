package com.example.repository;

import com.example.entity.ProfileEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>, PagingAndSortingRepository<ProfileEntity, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE ProfileEntity e SET e = :newEntity WHERE e.id = :entityId")
    int updateAttribute(@Param("entityId") Integer entityId, @Param("newEntity") ProfileEntity newEntity);

    @Transactional
    @Modifying
    @Query("update ProfileEntity  set name =?2, surname =?3 where id =?1")
    int updateDetail(Integer id, String name, String surname);
    Optional<ProfileEntity> findByPhone(String phone);
    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible = false where id =:id")
    int delete(@Param("id") Integer id);

    Optional<ProfileEntity> findByEmail(String email);

}
