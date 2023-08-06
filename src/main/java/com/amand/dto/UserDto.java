package com.amand.dto;


import lombok.Data;

import java.util.Date;

@Data
public class UserDto extends BaseDto<UserDto>{
    private String userName;

    private String password;

    private String repeatPassword;

    private String fullName;

    private Date date;

    private String phone;

    private String email;

    private String address;

    private int status;

}
