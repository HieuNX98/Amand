package com.amand.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto extends BaseDto {
    private String codeOrder;

    private String fullName;

    private String address;

    private String phone;

    private String email;

    private List<String> nameProducts;

    private int amount;

    private int price;

    private int totalPrice;


}
