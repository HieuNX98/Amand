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

    boolean updateStatusById(Integer id);

    int getTotalItem(Integer status);

}
