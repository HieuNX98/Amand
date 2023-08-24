package com.amand.form;

import com.amand.dto.RoleDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserForm extends BaseForm<UserForm> {

    private String userName;

    private String password;

    private String repeatPassword;

    private String fullName;

    private Date date;

    private String phone;

    private String email;

    private String address;

    private int status;

    private String roleCode;

    private List<String> roleCodes;

    private String roleName;

    private List<RoleForm> roleForms;



}
