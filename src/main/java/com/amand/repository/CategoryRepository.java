package com.amand.repository;

import com.amand.entity.CategoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @Query(value = "SELECT n.name FROM CategoryEntity n WHERE n.name = :name")
    String findOneNameByName(@Param("name") String name);

    @Query(value = "SELECT c.code FROM CategoryEntity c WHERE c.code = :code")
    String findOneCodeByCode(@Param("code") String code);

    @Query(value = "SELECT c.name FROM CategoryEntity c WHERE c.id = :id")
    String findOneNameById(@Param("id") Integer id);

    @Query(value = "SELECT c.code FROM CategoryEntity c WHERE c.id = :id")
    String findOneCodeById(@Param("id") Integer id);

    List<CategoryEntity> findAllByStatus(Pageable pageable, Integer Status);

    List<CategoryEntity> findAllByStatus(Integer status);

    @Query(value = "SELECT count(c) FROM CategoryEntity c WHERE c.status = :status")
    int countByStatus(@Param("status") Integer status);

    @Modifying
    @Query(value = "UPDATE CategoryEntity c SET c.status = :status WHERE c.id IN (:ids)")
    void updateStatusByIds(@Param("status") Integer status,
                            @Param("ids") List<Integer> ids);

}
