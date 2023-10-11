package com.amand.service;

import com.amand.dto.ProductOrderDto;

import java.util.List;

public interface IProductOrderService {
    List<ProductOrderDto> findAllByOrderId(Integer orderId, Integer status);

}
