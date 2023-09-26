package com.amand.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UserForm extends BaseForm {

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

    private List<String> roleCodes;

    private String roleName;

    private MultipartFile avatar;

}
