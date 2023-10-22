package com.amand.service;

import com.amand.dto.ProductDto;
import com.amand.entity.ProductEntity;
import com.amand.form.ProductForm;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IProductService {
    ProductDto save(ProductForm productForm);

    void save(ProductEntity productEntity);

    void save(List<ProductForm> productForms);

    Map<String, String> validate(ProductForm productForm, boolean isRegister);

    String validateFile(MultipartFile file);

    List<ProductDto> findAll(Pageable pageable, Integer status);

    int getTotalItem(Integer status);

    int getTotalItemBySearch(String name, String season, Integer categoryId, Boolean salePrice);

    ProductDto findOneById(Integer id);

    void updateStatus(List<Integer> ids, Integer status);

    void deleteProduct(List<Integer> ids);

    ProductDto outstandingProduct(Integer status, Integer limit);

    List<ProductDto> search(Pageable pageable, String name, String season, Integer categoryId, Boolean salePrice);

    ProductEntity findById(Integer id);

}
