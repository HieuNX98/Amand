package com.amand.service;

import com.amand.dto.ProductDto;
import com.amand.form.ProductForm;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IProductService {
    ProductDto save(ProductForm productForm);

    Map<String, String> validate(ProductForm productForm, boolean isRegister);

    List<ProductDto> findAll(Pageable pageable, Integer status);

    int getTotalItem(Integer status);

    int getTotalItemBySearch(String name, String season, Integer categoryId, Boolean salePrice);

    ProductDto findOneById(Integer id);

    void updateStatus(List<Integer> ids, Integer status);

    void deleteProduct(List<Integer> ids);

    ProductDto outstandingProduct(Integer status, Integer limit);

    List<ProductDto> search(Pageable pageable, String name, String season, Integer categoryId, Boolean salePrice);

}
