package com.amand.repository;

import com.amand.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {

        UserEntity findOneByUserNameAndStatus(String name , int status);

        @Query(value = "SELECT u.userName FROM UserEntity u WHERE u.userName = :username")
        String findOneByUserName(@Param("username") String userName);

        @Query(value = "SELECT u FROM UserEntity u INNER JOIN u.roles r WHERE r.code = :roleCode AND u.status = :status")
        List<UserEntity> findAllByRoleCodeAndStatus(@Param("roleCode") String roleCode, @Param("status") Integer status, Pageable pageable);

        UserEntity findOneById(Integer id);

        @Query(value = "SELECT count(u) FROM UserEntity u WHERE u.status = :status ")
        int countByStatus(@Param("status") Integer status);

}
