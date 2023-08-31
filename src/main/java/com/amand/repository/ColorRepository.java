package com.amand.repository;

import com.amand.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ColorRepository extends JpaRepository<ColorEntity, Integer> {
    @Query(value = "SELECT n.name FROM ColorEntity n WHERE n.name = :name")
    String findOneNameByName(@Param("name") String name);

    @Query(value = "SELECT c FROM ColorEntity c WHERE c.id IN (:ids)")
    List<ColorEntity> findAllByIds(@Param("ids") List<Integer> ids);


}
