package com.amand.service;

import com.amand.dto.OrderDto;
import com.amand.entity.OrderEntity;
import com.amand.form.OrderForm;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface IOrderService {
    OrderEntity save (OrderForm orderForm);

    Map<String, String> validatePay(BindingResult result);

    List<OrderDto> findAllByStatus(Pageable pageable, Integer status);

    List<OrderDto> findAllByStatus(Integer status);

    boolean updateStatusById(Integer id, Integer status);

    int getTotalItem(Integer status);

    OrderDto findById(Integer id, Integer status);

}
