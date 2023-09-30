package com.amand.form;

import lombok.Data;

@Data
public class OrderForm {
    private String email;

    private String fullName;

    private String phone;

    private String address;

    private String note;

    private Integer bagId;
}
