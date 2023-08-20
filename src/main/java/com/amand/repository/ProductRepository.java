package com.amand.repository;

import com.amand.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query(value = "SELECT p.name FROM ProductEntity p WHERE p.name = :name")
    String findOneNameByName(@Param("name") String name);

}
