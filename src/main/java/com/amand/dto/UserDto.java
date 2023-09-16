package com.amand.dto;


import lombok.Data;

import java.util.List;

@Data
public class UserDto extends BaseDto<UserDto>{
    private String userName;

    private String password;

    private String repeatPassword;

    private String fullName;

    private String date;

    private String phone;

    private String email;

    private String address;

    private int status;

    private String roleCode;

    private List<RoleDto> roleDtos;

    private String avatar;

}
