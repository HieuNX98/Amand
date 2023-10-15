package com.amand.repository;

import com.amand.entity.BagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BagRepository extends JpaRepository<BagEntity, Integer> {

    @Query(value = "SELECT b FROM BagEntity b WHERE b.user.id = :userId")
    BagEntity findByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT count(b) FROM BagEntity b WHERE b.user.id = :id")
    int countByUserId(@Param("id") Integer userId);

    @Query(value = "SELECT b FROM BagEntity b WHERE b.id = :id")
    BagEntity findOneById(@Param("id") Integer id);

    @Query(value = "select * from amand.bag where STR_TO_DATE(modified_date, '%Y-%m-%d')  <= STR_TO_DATE(:modifiedDate, '%Y-%m-%d')"
            , nativeQuery = true)
    List<BagEntity> findAllByModifiedDate(@Param("modifiedDate") String modifiedDate);

}
