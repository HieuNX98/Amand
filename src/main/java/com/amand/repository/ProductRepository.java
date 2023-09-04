package com.amand.repository;

import com.amand.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query(value = "SELECT p.name FROM ProductEntity p WHERE p.name = :name")
    String findOneNameByName(@Param("name") String name);

    @Query(value = "SELECT p.name FROM ProductEntity p WHERE p.id = :id")
    String findOneNameById(@Param("id") Integer id);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.category.id IN (:ids)")
    List<ProductEntity> findAllByCategoryId(@Param("ids") List<Integer> ids);



}
