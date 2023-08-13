package com.amand.service;

import com.amand.dto.CategoryDto;
import com.amand.form.CategoryForm;

import java.util.Map;

public interface ICategoryService {
    CategoryDto save(CategoryForm categoryForm);

    Map<String, String> validate(CategoryForm categoryForm);
}
