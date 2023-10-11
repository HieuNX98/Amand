package com.amand.service;

import com.amand.dto.ProductBagDto;
import com.amand.entity.ProductBagEntity;

import java.util.List;

public interface IProductBagService {
    List<ProductBagDto> findAllByBagId(Integer bagId);

    ProductBagEntity findById(Integer id);
}
