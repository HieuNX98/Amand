package com.amand.service;

import com.amand.entity.OrderEntity;
import com.amand.form.OrderForm;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface IOrderService {
    OrderEntity save (OrderForm orderForm);

    Map<String, String> validatePay(BindingResult result);
}
