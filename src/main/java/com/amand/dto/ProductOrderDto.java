package com.amand.dto;

import lombok.Data;

@Data
public class ProductOrderDto extends BaseDto {

    private String productName;

    private String colorName;

    private String sizeName;

    private Integer amount;

    private Double totalPrice;

    private Double oldPrice;

    private Double salePrice;

    private String image1;

    public ProductOrderDto(String productName, Double salePrice, String image1, Double oldPrice, Integer amount, String colorName, String sizeName ) {
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
