package com.amand.service;

import com.amand.dto.ProductDto;
import com.amand.form.ProductForm;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IProductService {
    ProductDto save(ProductForm productForm);

    Map<String, String> validate(ProductForm productForm);

    List<ProductDto> findAll(Pageable pageable);

    int getTotalItem();

}
