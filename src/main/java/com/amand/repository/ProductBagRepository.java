package com.amand.repository;

import com.amand.entity.ProductBagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductBagRepository extends JpaRepository<ProductBagEntity, Integer> {

    @Query(value = "SELECT p FROM ProductBagEntity p WHERE p.bag.id = :id AND p.sizeName = :sizeName AND p.colorName = :colorName")
    ProductBagEntity findAllByBagIdAndSizeNameAndColorName(@Param("id") Integer bagId,
                                                                 @Param("sizeName") String sizeName,
                                                                 @Param("colorName") String colorName);

    @Query(value = "SELECT p FROM ProductBagEntity p WHERE p.bag.id = :id")
    List<ProductBagEntity> findAllByBagId(@Param("id") Integer id);

    /*@Query(value = "DELETE FROM ProductBagEntity p WHERE p.bag.id = :bagId")
    @Modifying
    void deleteAllByBagId(@Param("bagId") Integer bagId);*/

    @Query(value = "DELETE FROM product_bag WHERE bag_id = :bagId", nativeQuery = true)
    @Modifying
    void deleteAllByBagId(@Param("bagId") Integer bagId);

    @Query(value = "SELECT p FROM ProductBagEntity p WHERE p.id = :id")
    ProductBagEntity findOneById(@Param("id") Integer id);

}
