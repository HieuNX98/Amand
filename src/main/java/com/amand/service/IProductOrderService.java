package com.amand.service;

import com.amand.dto.ProductOrderDto;
import com.amand.entity.ProductOrderEntity;

import java.util.List;

public interface IProductOrderService {
    List<ProductOrderDto> findAllByOrderId(Integer orderId, Integer status);

    List<ProductOrderDto> findAllByOrderId(Integer orderId);

    List<ProductOrderEntity> findByOrderId(Integer orderId, Integer status);

}
