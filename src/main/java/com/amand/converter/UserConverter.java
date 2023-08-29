package com.amand.converter;

import com.amand.dto.RoleDto;
import com.amand.dto.UserDto;
import com.amand.entity.RoleEntity;
import com.amand.entity.UserEntity;
import com.amand.form.RoleForm;
import com.amand.form.UserForm;
import com.amand.repository.RoleRepository;
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

    @Autowired
    private RoleConverter roleConverter;

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
        List<RoleDto> roleDtos = new ArrayList<>();
        for(RoleEntity role : userEntity.getRoles()) {
            RoleDto roleDto = roleConverter.toDto(role);
            roleDtos.add(roleDto);
            userDto.setRoleCode(role.getCode());
        }
        userDto.setRoleDtos(roleDtos);
        return userDto;
    }

    public UserEntity toEntity(UserEntity userEntity, UserForm userForm) {
        userEntity.setFullName(userForm.getFullName());
        userEntity.setPhone(userForm.getPhone());
        userEntity.setEmail(userForm.getEmail());
        userEntity.setAddress(userForm.getAddress());

        return userEntity;
    }


}
