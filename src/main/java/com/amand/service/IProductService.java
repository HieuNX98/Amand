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

    List<ProductDto> findAll(Pageable pageable);

    int getTotalItem();

    ProductDto findOneById(Integer id);

}
