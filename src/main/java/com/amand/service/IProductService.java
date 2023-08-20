package com.amand.service;

import com.amand.dto.ProductDto;
import com.amand.form.ProductForm;

import java.util.Map;

public interface IProductService {
    ProductDto save(ProductForm productForm);

    Map<String, String> validate(ProductForm productForm);

}
