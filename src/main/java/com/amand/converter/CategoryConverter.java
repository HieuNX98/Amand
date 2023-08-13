package com.amand.converter;

import com.amand.dto.CategoryDto;
import com.amand.entity.CategoryEntity;
import com.amand.form.CategoryForm;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public CategoryEntity toEntity(CategoryForm categoryForm) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryForm.getName());
        categoryEntity.setCode(categoryForm.getCode());
        return categoryEntity;
    }

    public CategoryDto toDto(CategoryEntity categoryEntity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(categoryEntity.getId());
        categoryDto.setName(categoryEntity.getName());
        categoryDto.setCode(categoryEntity.getCode());
        return categoryDto;
    }
}
