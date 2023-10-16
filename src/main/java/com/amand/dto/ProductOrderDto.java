package com.amand.dto;

import lombok.Data;

@Data
public class ProductOrderDto extends BaseDto {

    private String productName;

    private String colorName;

    private String sizeName;

    private int amount;

    private double totalPrice;

    private double oldPrice;

    private double salePrice;

    private String image1;

    public ProductOrderDto(String productName, double salePrice, String image1, double oldPrice, int amount, String colorName, String sizeName ) {
        this.productName = productName;
        this.salePrice = salePrice;
        this.image1 = image1;
        this.oldPrice = oldPrice;
        this.amount = amount;
        this.colorName = colorName;
        this.sizeName = sizeName;
    }

    public ProductOrderDto() {

    }
}
