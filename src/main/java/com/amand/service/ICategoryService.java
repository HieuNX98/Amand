package com.amand.service;

import com.amand.dto.CategoryDto;
import com.amand.form.CategoryForm;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ICategoryService {
    CategoryDto save(CategoryForm categoryForm);

    Map<String, String> validate(CategoryForm categoryForm);

    List<CategoryDto> findAll(Pageable pageable);

    int getTotalItem();
}
