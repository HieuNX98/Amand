package com.amand.repository;

import com.amand.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {
    RoleEntity findOneByCode(String code);

    @Query(value = "SELECT r FROM RoleEntity r WHERE r.code IN (:codes)")
    List<RoleEntity> findAllByCode(@Param("codes") List<String> codes);

}
