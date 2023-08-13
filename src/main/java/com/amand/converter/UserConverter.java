package com.amand.converter;

import com.amand.dto.UserDto;
import com.amand.entity.RoleEntity;
import com.amand.entity.UserEntity;
import com.amand.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;



@Component
public class UserConverter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEntity toEntity(UserForm userForm) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(userForm.getFullName());
        userEntity.setEmail(userForm.getEmail());
        userEntity.setUserName(userForm.getUserName());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userEntity.setPhone(userForm.getPhone());
        return userEntity;
    }
    public UserDto toDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setFullName(userEntity.getFullName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setUserName(userEntity.getUserName());
        userDto.setPhone(userEntity.getPhone());
        userDto.setStatus(userEntity.getStatus());
        userDto.setDate(userEntity.getDate());
        if (!CollectionUtils.isEmpty(userEntity.getRoles())) {
            for(RoleEntity role : userEntity.getRoles()) {
                userDto.setRoleCode(role.getCode());
            }
        }
        return userDto;
    }


}
