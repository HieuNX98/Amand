package com.amand.repository;

import com.amand.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SizeRepository extends JpaRepository<SizeEntity, Integer> {
    @Query(value = "SELECT s.name FROM SizeEntity s WHERE s.name = :name")
    String findOneNameByName(@Param("name") String name);

    @Query(value = "SELECT s.name FROM SizeEntity s WHERE s.id = :id")
    String findOneNameById(@Param("id") Integer id);

    SizeEntity findOneById(int id);

    @Query(value = "SELECT s FROM SizeEntity s WHERE s.id IN (:ids)")
    List<SizeEntity> findAllByIds(@Param("ids") List<Integer> ids);
}
