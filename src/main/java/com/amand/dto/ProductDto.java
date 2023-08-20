package com.amand.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDto extends BaseDto<ProductDto>{
    private String name;

    private Double oldPrice;

    private Double salePrice;

    private int amount;

    private String season;

    private List<String> colors;

    private List<String> sizes;

    private String categoryCode;

    private String image1;

    private String image2;

    private String image3;

    private String image4;

}
