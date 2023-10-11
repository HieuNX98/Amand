package com.amand.dto;

import lombok.Data;

@Data
public class ProductOrderDto extends BaseDto {

    private String productName;

    private String colorName;

    private String sizeName;

    private int amount;

    private double totalPrice;

}
