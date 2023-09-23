package com.amand.dto;

import lombok.Data;

@Data
public class BagDto extends BaseDto<BagDto> {
    private String productName;

    private String size;

    private Integer amount;

    private String color;

    private String productImage;

    private Double oldPrice;

}
