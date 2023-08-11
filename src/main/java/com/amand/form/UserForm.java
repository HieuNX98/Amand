package com.amand.form;

import com.amand.dto.RoleDto;
import lombok.Data;

import java.util.Date;

@Data
public class UserForm {

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

    private String roleName;

}
