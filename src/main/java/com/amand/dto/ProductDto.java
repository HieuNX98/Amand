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

    private List<ImageDto> images;

    private String color;

    private String size;
}
