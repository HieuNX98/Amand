package com.amand.converter;

import com.amand.dto.UserDto;
import com.amand.entity.RoleEntity;
import com.amand.entity.UserEntity;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEntity toEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(userDto.getFullName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setUserName(userDto.getUserName());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setPhone(userDto.getPhone());
        if(Strings.isNotBlank(userDto.getRoleCode())) {
            RoleEntity roleEntity = new RoleEntity();
            List<RoleEntity> roles = new ArrayList<>();
            roleEntity.setCode(userDto.getRoleCode());
            roles.add(roleEntity);
            userEntity.setRoles(roles);
        }
        return userEntity;
    }
    public UserDto toDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setFullName(userEntity.getFullName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setUserName(userEntity.getUserName());
        userDto.setPhone(userEntity.getPhone());
        if (!CollectionUtils.isEmpty(userEntity.getRoles())) {
            for(RoleEntity role : userEntity.getRoles()) {
                userDto.setRoleCode(role.getCode());
            }
        }
        return userDto;
    }


}
