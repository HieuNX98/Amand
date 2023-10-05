package com.amand.form;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class OrderForm {

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Bạn không được để trống thông tin họ tên")
    @Size(max = 50, message = "Họ tên không được dài quá 50 ký tự")
    private String fullName;


    @Pattern(regexp = "^\\d{10}$", message = "Thông tin số điện thoại không hợp lệ")
    @NotBlank(message = "Bạn không được để trống thông tin số điện thoại")
    private String phone;

    @NotBlank(message = "Bạn không được để trống thông tin địa chỉ")
    private String address;

    private String note;

    private Integer bagId;
}
