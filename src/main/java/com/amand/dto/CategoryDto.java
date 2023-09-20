package com.amand.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto extends BaseDto<CategoryDto>{
    private String name;

    private String code;

    private List<ProductDto> productDtos;

}
