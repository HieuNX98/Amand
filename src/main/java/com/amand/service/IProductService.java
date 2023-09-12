package com.amand.service;

import com.amand.dto.ProductDto;
import com.amand.form.ProductForm;
import com.amand.form.UserForm;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IProductService {
    ProductDto save(ProductForm productForm);

    Map<String, String> validate(ProductForm productForm, boolean isRegister);

    String validateHide(List<Integer> ids);

    List<ProductDto> findAll(Pageable pageable, Integer status);

    int getTotalItem(Integer status);

    ProductDto findOneById(Integer id);

    void updateStatus(List<Integer> ids, Integer status);

    void deleteProduct(List<Integer> ids);

}
