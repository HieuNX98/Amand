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

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.id = :id AND p.status = 1")
    ProductEntity findOneByIdAndStatus(@Param("id") Integer id);

    @Query(value = "SELECT p FROM ProductEntity p Where p.id = :id")
    ProductEntity findOneById(@Param("id") Integer id);

    @Query(value = "SELECT p.name FROM ProductEntity p WHERE p.id = :id")
    String findOneNameById(@Param("id") Integer id);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.status = :status AND p.category.id IN (:ids)")
    List<ProductEntity> findAllByCategoryIdsAndStatus(@Param("status") Integer status,
                                                      @Param("ids") List<Integer> ids);

    @Query(value = "SELECT p FROM ProductEntity p LEFT JOIN p.colors c WHERE c.id IN (:ids)")
    List<ProductEntity> findAllByColorIds(@Param("ids") List<Integer> ids);

    @Query(value = "SELECT count(p) FROM ProductEntity p WHERE p.status = :status")
    int countByStatus(@Param("status") Integer status);

    List<ProductEntity> findAllByStatus(Pageable pageable, Integer status);

    @Modifying
    @Query(value = "UPDATE ProductEntity p SET p.status = :status WHERE p.id IN (:ids)")
    void updateStatusByIds(@Param("ids") List<Integer> ids,
                           @Param("status") Integer status);

    @Query(value = "SELECT p FROM ProductEntity p LEFT JOIN p.sizes s WHERE s.id IN (:ids)")
    List<ProductEntity> findAllBySizeIds(@Param("ids") List<Integer> ids);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.id IN (:ids)")
    List<ProductEntity> findAllByIds(@Param("ids") List<Integer> ids);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.status = :status")
    List<ProductEntity> findAllByStatus(@Param("status") Integer status);

    @Query(value = "SELECT * FROM Product WHERE category_id = :categoryId AND status = :status ORDER BY created_date desc " +
            "LIMIT :limit ", nativeQuery = true)
    List<ProductEntity> findTop3ProductByCategoryIdAndStatus(@Param("categoryId") Integer id,
                                                             @Param("status") Integer status,
                                                             @Param("limit") Integer limit);

    @Query(value = "SELECT * FROM Product WHERE status = :status ORDER BY created_date desc LIMIT :limit", nativeQuery = true)
    ProductEntity findProductByRecentlyCreatedAndStatus(@Param("status") Integer status,
                                                        @Param("limit") Integer limit);

    @Query(value = "SELECT * FROM Product WHERE IF(:name is null, true, name LIKE %:name%)" +
            " AND IF(:season is null, true, season LIKE %:season%) " +
            " AND IF(:category is null, true, category_id = :category)" +
            " AND IF(:sale_price, sale_price is not null, true)" +
            " AND status = 1 ORDER BY created_date desc", nativeQuery = true)
    List<ProductEntity> findAllByProductNameAndSeasonAndCategory(@Param("name") String name,
                                                      @Param("season") String season,
                                                      @Param("category") Integer categoryId,
                                                      @Param("sale_price") Boolean salePrice,
                                                      Pageable pageable);
    @Query(value = "SELECT count(*) FROM Product WHERE IF(:name is null, true, name LIKE %:name%)" +
            " AND IF(:season is null, true, season LIKE %:season%)" +
            " AND IF(:category is null, true, category_id = :category)" +
            " AND IF(:sale_price, sale_price is not null, true)" +
            " AND status = 1", nativeQuery = true)
    int countBySearch(@Param("name") String name,
                      @Param("season") String season,
                      @Param("category") Integer categoryId,
                      @Param("sale_price") Boolean salePrice);


}
