package com.amand.repository;

import com.amand.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SizeRepository extends JpaRepository<SizeEntity, Integer> {
    @Query(value = "SELECT s.name FROM SizeEntity s WHERE s.name = :name")
    String findOneByName(@Param("name") String name);
}
