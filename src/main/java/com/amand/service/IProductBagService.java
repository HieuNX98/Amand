package com.amand.service;

import com.amand.dto.ProductBagDto;

import java.util.List;

public interface IProductBagService {
    List<ProductBagDto> findAllByBagId(Integer bagId);
}
