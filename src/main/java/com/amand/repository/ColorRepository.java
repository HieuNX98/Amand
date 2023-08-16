package com.amand.repository;

import com.amand.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColorRepository extends JpaRepository<ColorEntity, Integer> {
    @Query(value = "SELECT n.name FROM ColorEntity n WHERE n.name = :name")
    String findOneByName(@Param("name") String name);
}
