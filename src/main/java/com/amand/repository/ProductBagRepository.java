package com.amand.repository;

import com.amand.entity.ProductBagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductBagRepository extends JpaRepository<ProductBagEntity, Integer> {

    @Query(value = "SELECT p FROM ProductBagEntity p WHERE p.bag.id = :id AND p.size_name = :sizeName AND p.color_name = :colorName")
    ProductBagEntity findAllByBagIdAndSizeNameAndColorName(@Param("id") Integer bagId,
                                                                 @Param("sizeName") String sizeName,
                                                                 @Param("colorName") String colorName);

    @Query(value = "SELECT p FROM ProductBagEntity p WHERE p.bag.id = :id")
    List<ProductBagEntity> findAllByBagId(@Param("id") Integer id);
}
