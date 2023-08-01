package com.amand.repository;

import com.amand.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

        UserEntity findOneByUserNameAndStatus(String name , int status);

        @Query(value = "SELECT u.userName FROM UserEntity u WHERE u.userName = :username")
        String findOneByUserName(@Param("username") String userName);
}
