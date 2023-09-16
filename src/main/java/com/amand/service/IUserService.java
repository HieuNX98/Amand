package com.amand.service;

import com.amand.dto.UserDto;
import com.amand.form.UserForm;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IUserService {

    UserDto save(UserForm userForm);

    Map<String, String> validateRegisterAccount(UserForm userForm, boolean isAdmin);

    Map<String, String> validateUpdateAccount(UserForm userForm);

    Map<String, String> validateUpdateAccountInformation(UserForm userForm);

    List<UserDto> findAllByRoleCodeAndStatus(List<String> roleCodes, Pageable pageable, Integer status);

    UserDto updateAccountInformation(UserForm userForm);

    int getTotalItem(Integer status, String roleCode);

    UserDto findOneById(Integer id);

    void updateStatus(List<Integer> ids, int status);

    void deleteUser(List<Integer> ids);

    UserDto findOneByUserName(String userName);

}
