package com.amand.dto;

import lombok.Data;

@Data
public class BagDto extends BaseDto<BagDto> {
    private String size;

    private String amount;

    private String color;

}
