package com.amand.dto;

import lombok.Data;

@Data
public class CategoryDto extends BaseDto<CategoryDto>{
    private String name;

    private String code;

}
