package com.amand.repository;

import com.amand.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
        UserEntity findOneByUserNameAndStatus(String name , int status);
}
