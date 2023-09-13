package com.amand.repository;

import com.amand.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query(value = "SELECT p.name FROM ProductEntity p WHERE p.name = :name")
    String findOneNameByName(@Param("name") String name);

    @Query(value = "SELECT p.name FROM ProductEntity p WHERE p.id = :id")
    String findOneNameById(@Param("id") Integer id);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.status = :status AND p.category.id IN (:ids)")
    List<ProductEntity> findAllByCategoryIdAndStatus(@Param("status") Integer status,
                                                     @Param("ids") List<Integer> ids);

    @Query(value = "SELECT p FROM ProductEntity p LEFT JOIN p.colors c WHERE c.id IN (:ids)")
    List<ProductEntity> findAllByColorIds(@Param("ids") List<Integer> ids);

    @Query(value = "SELECT count(p) FROM ProductEntity p WHERE p.status = :status")
    int countByStatus(@Param("status") Integer status);

    List<ProductEntity> findAllByStatus(Pageable pageable, Integer status);

    @Modifying
    @Query(value = "UPDATE ProductEntity p SET p.status = (:status) WHERE p.id IN (:ids)")
    void updateStatusByIds(@Param("ids") List<Integer> ids,
                           @Param("status") Integer status);

    @Query(value = "SELECT p FROM ProductEntity p LEFT JOIN p.sizes s WHERE s.id IN (:ids)")
    List<ProductEntity> findAllBySizeIds(@Param("ids") List<Integer> ids);
}
