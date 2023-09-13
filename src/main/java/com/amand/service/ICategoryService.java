package com.amand.service;

import com.amand.dto.CategoryDto;
import com.amand.form.CategoryForm;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ICategoryService {
    CategoryDto save(CategoryForm categoryForm);

    Map<String, String> validate(CategoryForm categoryForm, boolean isRegister);

    List<CategoryDto> findAllByStatus(Pageable pageable, Integer status);

    int getTotalItem(Integer Status);

    List<CategoryDto> findAllByStatus(Integer status);

    CategoryDto findOneById(Integer id);

    void delete(List<Integer> ids);

    String validateHide(List<Integer> ids);

    void updateStatus(List<Integer> ids, Integer status);

    void deleteCategory(List<Integer> ids);

    String validateDelete(List<Integer> ids);

}
