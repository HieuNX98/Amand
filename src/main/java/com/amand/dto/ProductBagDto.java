package com.amand.dto;

import lombok.Data;

@Data
public class ProductBagDto extends BaseDto {

    private String colorName;

    private String sizeName;

    private Integer amount;

    private Integer productId;

    private ProductDto productDto;
}
