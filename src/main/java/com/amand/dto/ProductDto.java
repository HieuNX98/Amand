package com.amand.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDto extends BaseDto<ProductDto>{
    private String name;

    private int oldPrice;

    private int salePrice;

    private int amount;

    private String season;

    private String color;

    private String size;

    private String image1;

    private String image2;

    private String image3;

    private String image4;

    private String image5;
}
