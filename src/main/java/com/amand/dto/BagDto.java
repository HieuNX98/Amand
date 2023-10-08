package com.amand.dto;

import lombok.Data;

@Data
public class BagDto extends BaseDto {

    private String productName;

    private Integer amount;

    private Double totalPrice;

    private Integer userId;

    private Double subtotal;


}
