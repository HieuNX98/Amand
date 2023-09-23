package com.amand.repository;

import com.amand.entity.BagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BagRepository extends JpaRepository<BagEntity, Integer> {

    @Query("SELECT b FROM BagEntity b WHERE b.user.id = :userId")
    BagEntity findByUserId(@Param("userId") Integer userId);
}
